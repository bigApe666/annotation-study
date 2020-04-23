package entity;

import annotation.OssClass;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @创建时间 2020/4/21.
 * @开发人员 张戮
 */
@OssClass
public class Student {

    private String name;
    private String headImg;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static void main(String[] args) {
        Class<Student> studentClass = Student.class;
        Method[] methods = studentClass.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            System.err.println(method.toString());
        });
    }
}
