package com.ikikyou.practice.dto.query;

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
public class UserQueryDTO extends BaseQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7994365087923583806L;

    private String tel;

    private String email;

    private String username;

    private String nickName;

    private long roleId;
}
