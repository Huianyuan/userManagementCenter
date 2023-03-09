package com.example.umcenterbacked;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;
import sun.security.util.Password;

import java.nio.charset.StandardCharsets;
import java.util.zip.DeflaterInputStream;

@SpringBootTest
class UmCenterBackedApplicationTests {

    @Test
    void contextLoads() {
    }

    //加密测试
    @Test
    void testDigest() {
        String newPassword = DigestUtils.md5DigestAsHex(("123456"+"mypassword").getBytes());
        System.out.println(newPassword);
    }

}
