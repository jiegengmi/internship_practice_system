package com.ikikyou.practice.listener;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ikikyou.practice.enums.UserStatusEnums;
import com.ikikyou.practice.model.dto.UserDetail;
import com.ikikyou.practice.model.entity.system.SysUser;
import com.ikikyou.practice.service.SysUserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 用户导入监听器
 *
 * @author ikikyou
 * @date 2023/05/25 10:35
 */
@Slf4j
@Getter
public class UserExcelImportListener implements ReadListener<SysUser> {

    private final SysUserService userService;
    private final UserDetail baseUser;
    private final Date date;
    private final List<SysUser> userList = new ArrayList<>();
    private final Long deptId;
    private int count = 0, success = 0;
    private final boolean isUpdate;
    private List<SysUser> allUser;

    public UserExcelImportListener(SysUserService userService, UserDetail baseUser, Long deptId, boolean isUpdate) {
        this.userService = userService;
        this.baseUser = baseUser;
        date = new Date();
        this.deptId = deptId;
        this.isUpdate = isUpdate;
    }

    @Override
    public void invoke(SysUser sysUser, AnalysisContext analysisContext) {
        if (isUpdate) {
             if (CollectionUtil.isEmpty(allUser)) {
                 allUser = userService.list(new LambdaQueryWrapper<SysUser>()
                         .eq(deptId != null, SysUser::getDeptId, deptId)
                 );
             }
             if (CollectionUtil.isEmpty(allUser)) {
                 insertUser(sysUser);
             } else {
                 SysUser user = check(sysUser);
                 if (null == user) {
                     insertUser(sysUser);
                 } else {
                     BeanUtils.copyProperties(sysUser, user);
                     user.setUpdateTime(date);
                     user.setUpdateBy(baseUser.getUsername());
                     userService.saveOrUpdate(user);
                 }
             }
        } else {
            insertUser(sysUser);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (userService.saveBatch(userList)) {
            success += userList.size();
        }
        log.warn("用户批量导入结束:总计：{} 份记录，其中成功 {} 条数据", count, success);
    }

    private void insertUser(SysUser sysUser) {
        sysUser.setCreateBy(baseUser.getUsername());
        sysUser.setCreateTime(date);
        sysUser.setStatus(UserStatusEnums.ENABLE.getCode());
        //TODO 批量创建用户的默认密码~
        sysUser.setPassword(BCrypt.hashpw(sysUser.getTel() == null ? sysUser.getUserName() : sysUser.getTel(),
                                            BCrypt.gensalt()));
        if (deptId != null) {
            sysUser.setDeptId(deptId);
        }
        userList.add(sysUser);
        count++;
        if (userList.size() % 1000 == 0) {
            userService.saveBatch(userList);
            userList.clear();
            success += 1000;
        }
    }

    /**
     * 判断是否存在相同用户名的用户
     *
     * @param importUser 当前导入的user对象
     * @return null：不存在，做新增
     */
    private SysUser check(SysUser importUser) {
        return allUser.stream()
                .filter(user -> importUser.getUserName().equals(user.getUserName()))
                .findAny()
                .orElse(null);
    }
}
