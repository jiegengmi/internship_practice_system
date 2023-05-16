package com.ikikyou.practice.model.dto;

import com.ikikyou.practice.model.entity.system.SysMenu;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单对象
 * @author ikikyou
 * @date 2023/03/24 12:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4511810060756178658L;

    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 是否为外链（0是 1否）
     */
    private Integer isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    private Integer isCache;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    private Boolean hidden;

    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    private Boolean alwaysShow;

    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;

    /**
     * 子菜单列表
     */
    private List<MenuDTO> children;

    /**
     * 显示对象
     */
    private MenuRouteDTO meta;
}
