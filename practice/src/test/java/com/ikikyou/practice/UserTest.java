package com.ikikyou.practice;

import com.ikikyou.practice.dto.UserDTO;
import com.ikikyou.practice.dto.query.UserQueryDTO;
import com.ikikyou.practice.mapper.SysUserMapper;
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
    @Autowired
    SysUserMapper userMapper;

    @Test
    public void test(){
        System.out.println(BCrypt.checkpw("123456", "$2a$10$kiKC6bwcjpV/ICVGoq/h2OPb7IvOYUMYCoysOwwAIKU4jwwFOGZxq"));
    }
    @Test
    public void userInsert(){
        UserDTO dto = UserDTO.builder().userId(System.currentTimeMillis()).userName("ikikyou").nickName("管理员")
                .password("111111").tel("1231111111").sex("1").status("1").build();
        System.out.println(userService.insert(null));
    }

    @Test
    public void listTest(){
        UserQueryDTO queryDTO = new UserQueryDTO();
        System.out.println(userMapper.getUserList(queryDTO));
    }
}
