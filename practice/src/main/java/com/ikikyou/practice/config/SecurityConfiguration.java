package com.ikikyou.practice.config;

import cn.hutool.crypto.digest.BCrypt;
import com.ikikyou.practice.auth.UserDetailsServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author ikikyou
 * @date 2023/03/21 14:09
 */
@Configuration
public class SecurityConfiguration {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public AuthenticationProvider systemAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    /**
     *  安全过滤
     *   <a href="https://www.jianshu.com/p/b7c6b0cc87f0">作用参考</a>
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/*").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(withDefaults())
                .authenticationProvider(systemAuthenticationProvider());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
