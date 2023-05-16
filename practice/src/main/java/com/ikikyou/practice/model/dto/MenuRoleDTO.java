package com.ikikyou.practice.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色与菜单传递对象
 *
 * @author ikikyou
 * @date 2023/05/08 14:56
 */
@Data
public class MenuRoleDTO {

    /**
     * 已被赋予的菜单权限
     */
    private List<Long> checkedKeys;

    /**
     * 菜单权限
     */
    private List<MenuDTO> menus;
}
