package com.ikikyou.practice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.common.exception.BusinessException;
import com.ikikyou.practice.constant.BusinessConstant;
import com.ikikyou.practice.model.dto.DeptDTO;
import com.ikikyou.practice.model.entity.system.SysDept;
import com.ikikyou.practice.model.query.DeptQuery;
import com.ikikyou.practice.service.SysDeptService;
import com.ikikyou.practice.mapper.SysDeptMapper;
import com.ikikyou.practice.model.dto.DeptTreeDTO;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 25726
 * @description 针对表【sys_dept(部门表)】的数据库操作Service实现
 * @createDate 2023-04-21 09:19:05
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    final SysDeptMapper deptMapper;

    /**
     * 获取部门列表
     *
     * @param deptQuery 部门查询对象
     * @return {@link DeptTreeDTO} 列表
     */
    @Override
    public Result<List<DeptDTO>> getDeptList(DeptQuery deptQuery) {
        //TODO 数据权限
        return Result.ok(BeanUtil.copyToList(deptMapper.queryDept(deptQuery), DeptDTO.class));
    }

    /**
     * 查询部门列表（排除节点）
     *
     * @param deptId 部门id
     */
    @Override
    public Result<List<DeptDTO>> getDeptListAndExcludeChild(Long deptId) {
        List<SysDept> deptList = deptMapper.queryDept(new DeptQuery());
        deptList.removeIf(d -> d.getDeptId().intValue() == deptId
                || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), String.valueOf(deptId)));
        return Result.ok(BeanUtil.copyToList(deptList, DeptDTO.class));
    }

    /**
     * 获取部门信息
     *
     * @param deptId 部门id
     */
    @Override
    public Result<DeptDTO> getDeptInfo(Long deptId) {
        //TODO 数据权限
        return Result.ok(BeanUtil.copyProperties(deptMapper.getByDeptId(deptId), DeptDTO.class));
    }

    /**
     * 新增一个部门（组织机构）
     *
     * @param deptDTO 机构对象
     * @return 保存结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> save(DeptDTO deptDTO) {
        SysDept parentDept = deptMapper.getByDeptId(deptDTO.getParentId());
        if (!BusinessConstant.DEPT_NORMAL.equals(parentDept.getStatus())) {
            return Result.fail("选择的父级已停用");
        }
        deptDTO.setAncestors(parentDept.getAncestors() + "," + deptDTO.getParentId());
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(deptDTO, sysDept);
        sysDept.setCreateBy(SecurityUtil.getUserName());
        sysDept.setCreateTime(new Date());
        return saveOrUpdate(sysDept) ? Result.ok("新增成功") : Result.fail("新增失败");
    }

    /**
     * 修改一个部门（组织机构）
     *
     * @param deptDTO 机构对象
     * @return 保存结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> update(DeptDTO deptDTO) {
        SysDept newParentDept = deptMapper.getByDeptId(deptDTO.getParentId());
        SysDept oldDept = deptMapper.getByDeptId(deptDTO.getDeptId());
        if (null != newParentDept && null != oldDept) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            deptDTO.setAncestors(newAncestors);
            updateDeptChildren(deptDTO.getDeptId(), newAncestors, oldAncestors);
        }
        SysDept dept = BeanUtil.copyProperties(deptDTO, SysDept.class);
        dept.setUpdateBy(SecurityUtil.getUserName());
        dept.setUpdateTime(new Date());
        if (!this.saveOrUpdate(dept)) {
            throw new BusinessException("保存异常");
        }
        if (BusinessConstant.DEPT_NORMAL.equals(deptDTO.getStatus()) && StringUtils.isNotEmpty(deptDTO.getAncestors())
                && !StringUtils.equals("0", deptDTO.getAncestors())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatusNormal(dept.getAncestors());
        }
        return Result.ok("修改成功");
    }

    /**
     * 删除一个部门
     *
     * @param deptId 机构id
     * @return 保存结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> delete(Long deptId) {
        SysDept sysDept = this.getById(deptId);
        if (null == sysDept) {
            return Result.fail("未知id");
        }
        sysDept.setUpdateBy(SecurityUtil.getUserName());
        sysDept.setUpdateTime(new Date());
        sysDept.setDelFlag("2");
        return this.saveOrUpdate(sysDept) ? Result.ok("操作成功") : Result.fail("操作失败");
    }

    @Override
    public List<DeptTreeDTO> getDeptListTree(DeptQuery deptQuery) {
        if (ObjectUtil.isEmpty(deptQuery)) {
            return Collections.emptyList();
        }
        List<SysDept> sysDeptList = deptMapper.queryDept(deptQuery);
        List<DeptDTO> deptDTOList = buildDeptTree(BeanUtil.copyToList(sysDeptList, DeptDTO.class));
        return deptDTOList.stream().map(DeptTreeDTO::new).toList();
    }

    private List<DeptDTO> buildDeptTree(List<DeptDTO> deptList) {
        List<DeptDTO> returnList = new ArrayList<>();
        List<Long> tempList = deptList.stream().map(DeptDTO::getDeptId).toList();
        for (DeptDTO dept : deptList) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(deptList, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = deptList;
        }
        return returnList;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param ancestors 父级
     */
    private void updateParentDeptStatusNormal(String ancestors) {
        String[] ancestorArr = ancestors.split(",");
        List<Long> idList = Arrays.stream(ancestorArr).map(Long::parseLong).toList();
        deptMapper.updateDeptStatusNormal(idList);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    private void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<DeptDTO> list, DeptDTO targetDept) {
        // 得到子节点列表
        List<DeptDTO> childList = getChildList(list, targetDept);
        targetDept.setChildren(childList);
        for (DeptDTO tChild : childList) {
            if (!hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<DeptDTO> getChildList(List<DeptDTO> list, DeptDTO targetDept) {
        return list.stream()
                .filter(dept -> dept.getParentId() != null && dept.getParentId().equals(targetDept.getDeptId()))
                .toList();
    }

    /**
     * 是否有子节点
     *
     * @param list       部门列表
     * @param targetDept 目标部门
     */
    private boolean hasChild(List<DeptDTO> list, DeptDTO targetDept) {
        return list.stream()
                .filter(dept -> dept.getParentId() != null && dept.getParentId().equals(targetDept.getDeptId()))
                .findAny()
                .isEmpty();
    }
}




