package com.ikikyou.practice.service;

import com.ikikyou.practice.model.dto.DeptTypeDTO;
import com.ikikyou.practice.model.entity.system.SysType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author kikyou
* @description 针对表【sys_type(类型表)】的数据库操作Service
* @createDate 2023-06-01 16:08:48
*/
public interface SysTypeService extends IService<SysType> {

    /**
     * 获取所有类型
     *
     * @return  key:类型定义；value:{@link SysType}
     */
    Map<String, List<SysType>> getAll();

    /**
     * 获取部门类型结构树
     *
     * @return {@link DeptTypeDTO} 部门根类型
     */
    DeptTypeDTO getDeptTypeTree();


    /**
     * 获取部门（组织机构）的所有类型 （非树结构）
     *
     * @return {@link DeptTypeDTO} 所有类型
     */
    List<DeptTypeDTO> getDeptTypeList();
}
