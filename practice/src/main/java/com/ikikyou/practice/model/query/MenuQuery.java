package com.ikikyou.practice.model.query;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author hongx
 * @date 2023/05/06 15:41
 */
@Getter
@Setter
public class MenuQuery extends BaseQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = -7409286962848754039L;
    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单是否可见
     */
    private String visible;

    /**
     * 当前用户id
     */
    private Long userId;
}
