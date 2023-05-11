package com.ikikyou.practice.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * SpringSecurity的自定义注入对象
 * @author ikikyou
 * @date 2023/03/21 13:56
 */
@Setter
@Getter
public class UserDetail extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 3564634404075571585L;

    private Long userId;

    private String nickName;

    private String token;

    public UserDetail(String token, Long userId, String username, String nickName, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.nickName = nickName;
        this.token = token;
    }
}
