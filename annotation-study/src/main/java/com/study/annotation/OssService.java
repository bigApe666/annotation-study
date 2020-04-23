package com.study.annotation;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @auther zhangHongLu
 * @date 2020/4/22 0022 11:39
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Inherited
//singleton类型的bean引用一个prototype的bean时会出现问题
// 因为singleton只初始化一次，但prototype每请求一次都会有一个新的对象，
// prototype类型的bean是singleton类型bean的一个属性，理所当然不可能有新prototype的bean产生，与我们的要求不符
public @interface OssService {

}
