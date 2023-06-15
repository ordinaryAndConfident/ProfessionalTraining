package com.xiaoyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan("com.xiaoyu.dao")
public class MusicWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicWebsiteApplication.class,args);
    }
}
