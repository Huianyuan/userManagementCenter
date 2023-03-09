package com.example.umcenterbacked.service;
import java.util.Date;

import com.example.umcenterbacked.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/*
 * 用户服务测试
 * @param null
 * @return null
 */
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    //数据库插入测试
    @Test
    public void testAddUser() {
        User user = new User();
        user.setUserName("dogYupi");
        user.setUserAccount("123");
        user.setAvatarUrl("https://i2.hdslb.com/bfs/face/652eaddd7343c8d0737ff910ad1731f5b3d21e73.jpg@68w_68h.jpg");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");

        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);


    }

    //注册用户测试
    @Test
    void userRegister() {
        String userAccount = "ceshi";
        String userPassword = "";
        String checkPassword = "123456";
        // long result = userService.userRegister(userAccount, userPassword, checkPassword);
        // Assertions.assertEquals(-1, result);//非空校验
        //
        // userAccount="ce";
        // result = userService.userRegister(userAccount, userPassword, checkPassword);
        // Assertions.assertEquals(-1, result);//用户名长度>4
        //
        // userAccount="ceshi";
        // userPassword="123456";
        // result=userService.userRegister(userAccount, userPassword, checkPassword);
        // Assertions.assertEquals(-1, result);//密码长度<8,校验码<8
        //
        // userAccount="ce shi";
        // userPassword="12345678";
        // result=userService.userRegister(userAccount, userPassword, checkPassword);
        // Assertions.assertEquals(-1, result);//特殊字符校验
        //
        // checkPassword="123456789";
        // result=userService.userRegister(userAccount, userPassword, checkPassword);
        // Assertions.assertEquals(-1, result);//更改校验码长度>8。导致密码和
        //
        // userAccount="dogYupi";
        // checkPassword="12345678";
        // result=userService.userRegister(userAccount, userPassword, checkPassword);
        // Assertions.assertEquals(-1, result);//数据库用户账号查重
        //
        // userAccount="ceshi";
        // result=userService.userRegister(userAccount, userPassword, checkPassword);
        // Assertions.assertEquals(-1,result);//成功返回新注册账号ID

        userAccount="ceshiyuan";
        userPassword="12345678";
        checkPassword="12345678";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertTrue(result>0);


    }
}