package com.ikikyou.practice.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author ikikyou
 * @date 2023/03/21 13:56
 */
public class UserDetail extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 3564634404075571585L;

    private Long userId;

    private String nickName;

    public UserDetail(Long userId, String username, String nickName, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.nickName = nickName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
