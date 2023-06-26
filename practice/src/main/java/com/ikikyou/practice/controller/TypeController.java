package com.ikikyou.practice.controller;

import com.ikikyou.practice.model.dto.DeptTypeDTO;
import com.ikikyou.practice.service.SysTypeService;
import com.ikikyou.practice.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统业务类型控制层
 *
 * @author ikikyou
 * @date 2023/06/02 10:25
 */
@RestController
@RequestMapping("/type")
@RequiredArgsConstructor
public class TypeController {

    private final SysTypeService typeService;


    /**
     * 获取部门的业务类型（树结构）
     *
     * @return {@link DeptTypeDTO} 根类型
     */
    @GetMapping("/dept")
    public Result<DeptTypeDTO> getDeptType() {
        return Result.ok(typeService.getDeptTypeTree());
    }

    /**
     * 获取部门的业务类型（非树结构）
     *
     */
    @GetMapping("/dept/list")
    public Result<List<DeptTypeDTO>> getDeptListType() {
        return Result.ok(typeService.getDeptTypeList());
    }
}
