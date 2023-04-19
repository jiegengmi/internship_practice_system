package com.ikikyou.practice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ikikyou.practice.entity.system.SysDictData;
import com.ikikyou.practice.entity.system.SysDictType;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
* @author 25726
* @description 针对表【sys_dict_type(字典类型表)】的数据库操作Service
* @createDate 2023-04-19 16:49:23
*/
public interface SysDictTypeService extends IService<SysDictType> {

}
