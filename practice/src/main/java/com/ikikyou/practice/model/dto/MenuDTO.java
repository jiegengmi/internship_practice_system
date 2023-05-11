package com.ikikyou.practice.model.dto;

import com.ikikyou.practice.model.entity.system.SysMenu;
import lombok.*;

import java.io.Serial;
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
@EqualsAndHashCode(callSuper = true)
public class MenuDTO extends SysMenu {

    @Serial
    private static final long serialVersionUID = -4511810060756178658L;

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
