package com.example.umcenterbacked;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com/example/umcenterbacked/mapper")
public class UmCenterBackedApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmCenterBackedApplication.class, args);
    }

}
