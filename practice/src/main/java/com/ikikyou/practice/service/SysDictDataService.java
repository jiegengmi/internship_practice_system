package com.ikikyou.practice.service;

import com.ikikyou.practice.model.entity.system.SysDictData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ikikyou.practice.utils.Result;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_dict_data(字典数据表)】的数据库操作Service
* @createDate 2023-04-19 16:51:24
*/
public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 根据字典类型查找数据
     * @param dictType 类型
     */
    Result<List<SysDictData>> selectDictDataByType(String dictType);

    Result<SysDictData> selectDictDataById(Long dictCode);
}
