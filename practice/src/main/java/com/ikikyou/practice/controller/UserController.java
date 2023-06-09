package com.ikikyou.practice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ikikyou.practice.model.dto.UserDTO;
import com.ikikyou.practice.model.dto.UserIndexInfoDTO;
import com.ikikyou.practice.model.dto.UserInfoDTO;
import com.ikikyou.practice.model.dto.UserUpdateDTO;
import com.ikikyou.practice.model.query.UserQuery;
import com.ikikyou.practice.service.SysUserService;
import com.ikikyou.practice.service.UserInfoService;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ikikyou
 * @date 2023/03/24 08:48
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "用户")
public class UserController {

    final SysUserService userService;
    final UserInfoService userInfoService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user:list')")
    public Result<Page<UserDTO>> getUserList(UserQuery userQuery) {
        return Result.ok(userService.getUserList(userQuery));
    }

    @GetMapping({"/{userId}", "/"})
    @PreAuthorize("hasAuthority('user:info')")
    public Result<UserUpdateDTO> getUserInfo(@PathVariable(required = false) Long userId) {
        return userService.getUserInfo(userId);
    }

    /**
     * 获取当前登录用户、角色、权限
     */
    @GetMapping("/info")
    public Result<UserInfoDTO> getByCurrUser() {
        return userInfoService.getInfoByUserName(SecurityUtil.getUserName());
    }


    @PostMapping("/insert")
    @ApiOperation(value = "新增用户")
    @PreAuthorize("hasAuthority('user:insert')")
    public Result<Void> add(@RequestBody @Valid UserUpdateDTO userDTO) {
        return userService.insert(userDTO);
    }

    /**
     * 修改用户
     *
     * @param userDTO 用户信息
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改用户")
    @PreAuthorize("hasAuthority('user:update')")
    public Result<Void> update(@RequestBody @Valid UserUpdateDTO userDTO) {
        return userService.update(userDTO);
    }

    /**
     * 根据用户id获取单个用户信息
     *
     * @param id 用户id
     */
    @GetMapping("/getById")
    @PreAuthorize("hasAuthority('user:info')")
    public Result<UserDTO> getById(@RequestParam Long id) {
        return userService.getUserById(id);
    }

    /**
     * 根据用户名获取单个用户信息
     *
     * @param username 用户账户
     */
    @GetMapping("/getByName")
    @PreAuthorize("hasAuthority('user:info')")
    public Result<UserDTO> getByName(@RequestParam String username) {
        return userService.getUserByName(username);
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除用户")
    @PreAuthorize("hasAuthority('user:delete')")
    public Result<Void> deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    /**
     * 首页用户模块信息
     *
     * @return {@link UserIndexInfoDTO}
     */
    @GetMapping("/login/info")
    public Result<UserIndexInfoDTO> getIndexInfo(){
        return userService.getUserIndexInfo();
    }

    /**
     * excel批量用户导入
     *
     * @param file excel文件
     * @param deptId 部门id
     * @return 导入结果
     */
    @PostMapping("/excel/import")
    public Result<Void> batchImport(MultipartFile file, Long deptId, int updateSupport) {
        return userService.userInfoImport(file, deptId, updateSupport);
    }

    /**
     * 下载excel导入模板
     *
     */
    @PostMapping("/excel/template")
    public void downloadExcelTemplate(HttpServletResponse response) {
        userService.downloadExcelTemplate(response);
    }

    /**
     * 导出用户数据（excel形式）
     */
    @PostMapping("/excel/export")
    public void excelExport(HttpServletResponse response) {
        userService.downloadExcelTemplate(response);
    }
}
