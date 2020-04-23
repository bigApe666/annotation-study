package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @创建时间 2020/4/21.
 * @开发人员 张戮
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface OssClass {
}
