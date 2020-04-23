package com.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.study.dao"})
public class AnnotationStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnotationStudyApplication.class, args);
    }

}
