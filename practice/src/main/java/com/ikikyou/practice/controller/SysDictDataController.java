package com.ikikyou.practice.controller;

import com.ikikyou.practice.model.entity.system.SysDictData;
import com.ikikyou.practice.service.SysDictDataService;
import com.ikikyou.practice.service.SysDictTypeService;
import com.ikikyou.practice.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hongx
 * @date 2023/04/19 16:49
 */
@RestController
@RequestMapping("/dict/data")
@RequiredArgsConstructor
public class SysDictDataController {

    private final SysDictDataService dictDataService;
    private final SysDictTypeService dictTypeService;

    /**
     * 查询字典数据详细
     */
    @PreAuthorize("hasAnyAuthority('system:dict:query')")
    @GetMapping(value = "/{dictCode}")
    public Result<SysDictData> getInfo(@PathVariable Long dictCode) {
        return dictDataService.selectDictDataById(dictCode);
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public Result<List<SysDictData>> dictType(@PathVariable String dictType) {
        return dictDataService.selectDictDataByType(dictType);
    }
}

