package com.study.annotation;

import java.lang.annotation.*;

/**
 * @auther zhangHongLu
 * @date 2020/4/22 0022 10:46
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface OssBusinessId {
}
