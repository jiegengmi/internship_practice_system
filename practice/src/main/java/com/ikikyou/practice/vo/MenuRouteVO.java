package com.ikikyou.practice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hongx
 * @date 2023/04/19 09:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuRouteVO {
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private String icon;

    /**
     * 设置为true，则不会被 <keep-alive>缓存
     */
    private boolean noCache;

    /**
     * 内链地址（http(s)://开头）
     */
    private String link;

    public MenuRouteVO(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public MenuRouteVO(String title, String icon, String link) {
        this.title = title;
        this.icon = icon;
        this.link = link;
    }
}
