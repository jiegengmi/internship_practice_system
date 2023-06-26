package com.ikikyou.practice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.enums.SysTypeEnums;
import com.ikikyou.practice.model.dto.DeptTypeDTO;
import com.ikikyou.practice.model.entity.system.SysType;
import com.ikikyou.practice.service.SysTypeService;
import com.ikikyou.practice.mapper.SysTypeMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 想尽量设计为可扩展的
 *
 * @author 25726
 * @description 针对表【sys_type(类型表)】的数据库操作Service实现
 * @createDate 2023-06-01 16:08:48
 */
@Service
public class SysTypeServiceImpl extends ServiceImpl<SysTypeMapper, SysType> implements SysTypeService {

    /**
     * 获取所有类型
     *
     * @return 分业务后的类型
     */
    @Override
    @CachePut(cacheNames = "sysType")
    public Map<String, List<SysType>> getAll() {
        return this.list()
                .stream()
                .collect(
                        Collectors.toMap(SysType::getTypeDefine, Collections::singletonList, (oldType, newType) -> {
                            List<SysType> typeList = new ArrayList<>(oldType);
                            typeList.addAll(newType);
                            return typeList;
                        })
                );
    }

    /**
     * 获取部门类型结构树
     *
     * @return {@link DeptTypeDTO}
     */
    @Override
    public DeptTypeDTO getDeptTypeTree() {
        return buildDeptTree(getAll().get(SysTypeEnums.TYPE_DEPT.getTypeDefine()));
    }

    /**
     * 获取部门（组织机构）的所有类型 （非树结构）
     *
     * @return {@link DeptTypeDTO} 所有类型
     */
    @Override
    public List<DeptTypeDTO> getDeptTypeList() {
        return BeanUtil.copyToList(getAll().get(SysTypeEnums.TYPE_DEPT.getTypeDefine()), DeptTypeDTO.class);
    }

    /**
     * 构建部门类型树
     *
     * @param typeList 所有部门类型数据
     * @return {@link DeptTypeDTO} 根节点
     */
    public DeptTypeDTO buildDeptTree(List<SysType> typeList) {
        List<DeptTypeDTO> deptTypeList = BeanUtil.copyToList(typeList, DeptTypeDTO.class);
        DeptTypeDTO root = new DeptTypeDTO();
        Map<Long, DeptTypeDTO> map = deptTypeList
                .stream()
                .collect(Collectors.toMap(DeptTypeDTO::getId, deptType -> deptType));
        for (DeptTypeDTO deptType : deptTypeList) {
            DeptTypeDTO parent = map.get(deptType.getTypeParent());
            if (parent != null) {
                List<DeptTypeDTO> children = parent.getChildren() == null ? new ArrayList<>() : parent.getChildren();
                children.add(deptType);
                parent.setChildren(children);
            } else {
                root = deptType;
            }
        }
        return root;
    }

}




