package com.ikikyou.practice.dto.query;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author hongx
 * @date 2023/03/27 15:21
 */
public class UserQueryDTO extends BaseQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7994365087923583806L;

    private String email;

    private String username;

    private String nickName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
