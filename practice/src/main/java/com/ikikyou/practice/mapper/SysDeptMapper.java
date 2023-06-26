package com.ikikyou.practice.mapper;

import com.ikikyou.practice.model.entity.system.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ikikyou.practice.model.query.DeptQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_dept(部门表)】的数据库操作Mapper
* @createDate 2023-04-21 09:19:05
* @Entity com.ikikyou.practice.model.entity.system.SysDept
*/
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 获取部门列表
     *
     * @param deptQuery 查询对象
     * @return {@link SysDept}
     */
    List<SysDept> queryDept(DeptQuery deptQuery);

    /**
     * 获取部门下所有信息
     *
     * @param deptId 目标部门id
     * @return {@link SysDept}
     */
    SysDept getByDeptId(Long deptId);

    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    List<SysDept> selectChildrenDeptById(Long deptId);

    /**
     * 修改子元素关系
     *
     * @param deptList 子元素
     * @return 结果
     */
     int updateDeptChildren(@Param("deptList") List<SysDept> deptList);

    /**
     * 修改所在部门正常状态
     *
     * @param deptIds 部门ID组
     */
    void updateDeptStatusNormal(List<Long> deptIds);
}




