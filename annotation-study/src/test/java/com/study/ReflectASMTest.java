package com.study;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.study.config.OssEnvironment;
import com.study.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @auther zhangHongLu
 * @date 2020/4/22 0022 15:07
 */
@SpringBootTest
public class ReflectASMTest {
    public static Integer count = 50000000;


    // 1823ms 1816ms
    @Test
    public void testReflectASMField() {

        long start = System.currentTimeMillis();

        Student student = new Student();
        student.setStudentId(1L);
        student.setHeadImg("小明.jpg");
        MethodAccess methodAccess = MethodAccess.get(Student.class);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            List<Field> fieldList = OssEnvironment.getFieldMap().get(student.getClass());

            fieldList.stream().map(Field::getName).forEach(name -> {
                stringBuilder.append("get").append(name.substring(0,1).toUpperCase()).append(name.substring(1));
                String methodName = stringBuilder.toString();
                Object invoke = methodAccess.invoke(student, methodName);
                stringBuilder.delete(0,stringBuilder.length());
            });

        }

        long end = System.currentTimeMillis();
        System.err.println("普通对象创建耗时：" + (end - start) + "ms");
    }

    // 1867ms 1737ms 1770ms 1594ms
    @Test
    public void testJavaReflectField() {
        long start = System.currentTimeMillis();

        Student student = new Student();
        student.setStudentId(1L);
        student.setHeadImg("小明.jpg");

        MethodAccess methodAccess = MethodAccess.get(Student.class); //模拟最开始初始化步骤，实际代码只执行for循环

        for (int i = 0; i < count; i++) {

            List<Field> fieldList = OssEnvironment.getFieldMap().get(student.getClass());
            fieldList.stream().forEach(field -> {
                try {
                    Object o = field.get(student);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

        }

        long end = System.currentTimeMillis();
        System.err.println("普通反射获取属性耗时：" + (end - start) + "ms");
    }

    //4213ms 4283ms 4343ms
    @Test
    public void testJavaReflectMethod() throws NoSuchMethodException {
        long start = System.currentTimeMillis();

        Student student = new Student();
        student.setStudentId(1L);
        student.setHeadImg("小明.jpg");

        MethodAccess methodAccess = MethodAccess.get(Student.class); //模拟最开始初始化步骤，实际代码只执行for循环

        Class<? extends Student> aClass = student.getClass();
        Method method = aClass.getMethod("getStudentId");
        for (int i = 0; i < count; i++) {

            List<Field> fieldList = OssEnvironment.getFieldMap().get(student.getClass());
            fieldList.stream().map(Field::getName).forEach(name -> {
                try {
                    method.invoke(student);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }

        long end = System.currentTimeMillis();
        System.err.println("普通反射获取方法耗时：" + (end - start) + "ms");
    }

    //普通测试
    @Test
    public void testOrdinaryMethod(){
        long start = System.currentTimeMillis();

        Student student = new Student();
        student.setStudentId(1L);
        student.setHeadImg("小明.jpg");
        for (int i = 0; i < count; i++) {
            Long studentId = student.getStudentId();
        }

        long end = System.currentTimeMillis();
        System.err.println("普通反射获取方法耗时：" + (end - start) + "ms");
    }
}
