package com.ikikyou.practice.dto.query;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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

    private String userName;

    private String nickName;

    private Long roleId;

    private Long deptId;
}
