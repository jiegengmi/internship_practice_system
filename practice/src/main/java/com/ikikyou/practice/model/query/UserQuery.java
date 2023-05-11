package com.ikikyou.practice.model.query;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户查询对象
 * @author ikikyou
 * @date 2023/03/27 15:21
 */
@Setter
@Getter
public class UserQuery extends BaseQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = -7994365087923583806L;
    /**
     * 联系方式
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 部门id
     */
    private Long deptId;
}
