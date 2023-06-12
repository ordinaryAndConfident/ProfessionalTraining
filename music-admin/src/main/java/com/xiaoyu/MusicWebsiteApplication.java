package com.xiaoyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author YuSir
 * @version 1.0
 * @description: TODO
 * @date 2022/10/18 21:59
 */
@SpringBootApplication
@MapperScan("com.xiaoyu.dao")
public class MusicWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicWebsiteApplication.class,args);
    }
}
