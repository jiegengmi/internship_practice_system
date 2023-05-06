package com.ikikyou.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.entity.system.SysDictData;
import com.ikikyou.practice.service.SysDictDataService;
import com.ikikyou.practice.mapper.SysDictDataMapper;
import com.ikikyou.practice.utils.Result;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
* @author 25726
* @description 针对表【sys_dict_data(字典数据表)】的数据库操作Service实现
* @createDate 2023-04-19 16:51:24
*/
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService{

    private final SysDictDataMapper dictDataMapper;

    @Override
    public Result<List<SysDictData>> selectDictDataByType(String dictType) {
        if (StringUtils.isEmpty(dictType)) {
            return Result.ok(Collections.emptyList(),"字典类型为空");
        }
        QueryWrapper<SysDictData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0).eq("dict_type", dictType).orderByAsc("dict_sort");
        return Result.ok(dictDataMapper.selectList(queryWrapper));
    }

    @Override
    public Result<SysDictData> selectDictDataById(Long dictCode) {
        return Result.ok(getById(dictCode));
    }
}




