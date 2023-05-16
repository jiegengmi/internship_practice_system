package com.ikikyou.practice.controller;

import com.ikikyou.practice.model.dto.DeptDTO;
import com.ikikyou.practice.model.entity.system.SysDept;
import com.ikikyou.practice.model.query.DeptQuery;
import com.ikikyou.practice.service.SysDeptService;
import com.ikikyou.practice.utils.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门（组织机构）管理
 *
 * @author ikikyou
 * @date 2023/04/21 09:53
 */
@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
public class DeptController {

    final SysDeptService deptService;

    /**
     * 获取部门（组织机构）列表
     *
     * @param deptQuery 查询对象
     * @return {@link DeptDTO} 列表
     */
    @GetMapping("/list")
    public Result<List<DeptDTO>> getDeptList(DeptQuery deptQuery) {
        return deptService.getDeptList(deptQuery);
    }

    /**
     *获取部门（组织机构）列表（排除节点）
     *
     * @param deptId 部门id
     * @return {@link DeptDTO} 列表
     */
    @GetMapping("/list/exclude/{deptId}")
    public Result<List<DeptDTO>> excludeChild(@PathVariable Long deptId) {
        return deptService.getDeptListAndExcludeChild(deptId);
    }



    /**
     * 修改部门信息
     *
     * @param deptDTO 部门传递对象
     */
    @PreAuthorize("hasAuthority('dept:update')")
    @PutMapping
    public Result<Void> update(@RequestBody @Valid DeptDTO deptDTO) {
        return deptService.update(deptDTO);
    }

    /**
     * 新增部门信息
     *
     * @param deptDTO 部门传递对象
     */
    @PreAuthorize("hasAuthority('dept:insert')")
    @PostMapping
    public Result<Void> save(@RequestBody @Valid DeptDTO deptDTO) {
        return deptService.save(deptDTO);
    }

    /**
     * 获取部门相关信息
     *
     * @param deptId 部门id
     * @return {@link DeptDTO}
     */
    @GetMapping("{deptId}")
    public Result<DeptDTO> getById(@PathVariable Long deptId) {
        return deptService.getDeptInfo(deptId);
    }

    /**
     * 删除一共部门信息（软删除）
     *
     * @param deptId 部门id
     */
    @DeleteMapping("{deptId}")
    public Result<Void> delete(@PathVariable Long deptId) {
        return deptService.delete(deptId);
    }


    /**
     * 获取部门（组织机构）树
     *
     * @param deptQuery 部门
     * @return {@link DeptDTO} 列表
     */
    @GetMapping("/tree")
    public Result<List<DeptDTO>> getDeptTree(DeptQuery deptQuery) {
        return Result.ok(deptService.getDeptListTree(deptQuery));
    }
}
