package com.ikikyou.practice;

import com.ikikyou.practice.model.query.UserQuery;
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

    }

    @Test
    public void listTest(){
        UserQuery queryDTO = new UserQuery();
//        System.out.println(userMapper.getUserList(queryDTO));
    }
}
