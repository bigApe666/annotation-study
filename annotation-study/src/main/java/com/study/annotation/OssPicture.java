package com.study.annotation;

import java.lang.annotation.*;

/**
 * @auther zhangHongLu
 * @date 2020/4/22 0022 10:48
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface OssPicture {
    String bizType();//业务类型
    int format();//操作
}
