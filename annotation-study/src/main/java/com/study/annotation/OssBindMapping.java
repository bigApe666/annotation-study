package com.study.annotation;

import java.lang.annotation.*;

/**
 * @auther zhangHongLu
 * @date 2020/4/22 0022 09:13
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface OssBindMapping {
    int operate() default 1;//1 添加 2 修改
}
