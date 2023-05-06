package com.ikikyou.practice.controller;

import com.ikikyou.practice.entity.system.SysDept;
import com.ikikyou.practice.service.SysDeptService;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.vo.DeptVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hongx
 * @date 2023/04/21 09:53
 */
@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
public class DeptController {

    final SysDeptService deptService;

    @GetMapping("/tree")
    public Result<List<DeptVO>> getDeptList(SysDept dept) {
        return Result.ok(deptService.getDeptListTree(dept));
    }
}
