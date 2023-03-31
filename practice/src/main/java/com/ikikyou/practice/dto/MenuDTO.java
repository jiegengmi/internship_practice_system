package com.ikikyou.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 菜单对象
 * @author hongx
 * @date 2023/03/24 12:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {

    private Long id;

    /**
     * 菜单名
     */
    private String title;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * icon
     */
    private String icon;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 子菜单列表
     */
    private List<MenuDTO> children;
}
