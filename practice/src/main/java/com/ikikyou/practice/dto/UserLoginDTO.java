package com.ikikyou.practice.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author hongx
 * @date 2023/03/27 17:13
 */
@Data
public class UserLoginDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6299048783755999149L;

    private String username;
    private String password;
}
