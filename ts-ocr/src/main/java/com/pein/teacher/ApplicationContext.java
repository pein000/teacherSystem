package com.pein.teacher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by qiuwei on 2017/2/27.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.pein.teacher"})
public class ApplicationContext{

    public static void main(String[] args) {
        SpringApplication.run(ApplicationContext.class, args);
    }


}
