package com.ikikyou.practice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ikikyou.practice.model.dto.UserDTO;
import com.ikikyou.practice.model.dto.UserIndexInfoDTO;
import com.ikikyou.practice.model.dto.UserUpdateDTO;
import com.ikikyou.practice.model.query.UserQuery;
import com.ikikyou.practice.model.entity.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ikikyou.practice.utils.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 25726
 * @description 针对表【sys_user(用户信息表)】的数据库操作Service
 * @createDate 2023-04-21 11:07:14
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户实体
     */
    SysUser findByName(String username);

    /**
     * 新增一个用户
     *
     * @param userDTO 用户信息
     */
    Result<Void> insert(UserUpdateDTO userDTO);

    /**
     * 修改用户信息
     *
     * @param userDTO 用户信息
     */
    Result<Void> update(UserUpdateDTO userDTO);

    /**
     * 分页查询用户列表
     *
     * @param userQuery 用户查询对象
     */
    Page<UserDTO> getUserList(UserQuery userQuery);

    /**
     * 根据用户id获取用户信息
     *
     * @param uid 用户id
     */
    Result<UserDTO> getUserById(Long uid);

    /**
     * 根据用户获取用户信息
     *
     * @param username 用户名
     */
    Result<UserDTO> getUserByName(String username);

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 删除是否成功
     */
    Result<Void> deleteById(Long id);

    /**
     * 获取用户、用户角色、职位
     *
     * @param userId 用户id
     */
    Result<UserUpdateDTO> getUserInfo(Long userId);

    /**
     * 获取用户首页数据
     *
     * @return {@link UserIndexInfoDTO}
     */
    Result<UserIndexInfoDTO> getUserIndexInfo();

    /**
     * 用户信息批量导入（excel）
     *
     * @param file excel文件
     * @param deptId 部门（组织）id
     * @param updateSupport 是否支持更新 0：不更新 其他：更新
     * @return 导入结果
     */
    Result<Void> userInfoImport(MultipartFile file, Long deptId, int updateSupport);

    /**
     * 下载用户导入模板
     *
     */
    void downloadExcelTemplate(HttpServletResponse response);

    /**
     * 导出数据
     * @param response {@link HttpServletResponse} 文件流
     */
    void downloadExcelData(HttpServletResponse response);
}
