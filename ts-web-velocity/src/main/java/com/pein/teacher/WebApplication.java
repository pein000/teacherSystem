package com.pein.teacher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by qiuwei on 2017/2/27.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.pein.teacher"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
