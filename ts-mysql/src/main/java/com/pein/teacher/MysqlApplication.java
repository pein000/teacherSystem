package com.pein.teacher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by qiuwei on 2017/3/2.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.pein.teacher"})
public class MysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(MysqlApplication.class, args);
    }

}
