package com.ikikyou.practice;

import com.ikikyou.practice.dto.UserDTO;
import com.ikikyou.practice.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author ikikyou
 * @date 2023/03/31 09:23
 */
@SpringBootTest
public class UserTest {

    @Autowired
    SysUserService userService;

    @Test
    public void test(){
        System.out.println(BCrypt.checkpw("123456", "$2a$10$kiKC6bwcjpV/ICVGoq/h2OPb7IvOYUMYCoysOwwAIKU4jwwFOGZxq"));
    }
    @Test
    public void userInsert(){
        UserDTO dto = UserDTO.builder().id(System.currentTimeMillis()).name("ikikyou").nickName("管理员")
                .password("111111").age(24).tel("1231111111").sex(1).status(1).build();
        System.out.println(userService.insert(dto));
    }
}
