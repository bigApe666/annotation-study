package com.study;

import com.study.entity.Student;
import com.study.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

@SpringBootTest
class AnnotationTests {



    public static Integer count = 2000000000;


    //普通方法创建对象 40ms 46ms 40ms
    @Test
    public void testCommonMethodsCreateObjects() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            Student user = new Student();
        }

        long end = System.currentTimeMillis();
        System.err.println("普通对象创建耗时：" + (end - start) + "ms");
    }

    //加载并实例化 反正就很长
    @Test
    public void TestReflectMethodsCreateObjects() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            Student user = (Student) Class.forName("com.study.entity.Student").newInstance();
        }

        long end = System.currentTimeMillis();
        System.err.println("反射对象创建耗时：" + (end - start) + "ms");
    }

    //反射方法加载 922934ms
    @Test
    public void TestReflectMethodsOnload() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            Class<Student> aClass = (Class<Student>) Class.forName("com.study.entity.Student");
        }

        long end = System.currentTimeMillis();
        System.err.println("反射对象加载耗时：" + (end - start) + "ms");
    }

    //通过对象获取类文件 43ms 42ms 37ms
    @Test
    public void TestCreateObjects() throws Exception {
        long start = System.currentTimeMillis();

        Student student = new Student();
        for (int i = 0; i < count; i++) {
            Class<Student> aClass =( Class<Student>)student.getClass();
        }

        long end = System.currentTimeMillis();
        System.err.println("通过对象获取类测试：" + (end - start) + "ms");
    }

    //带缓存的对象创建 12825ms 12346ms 12363ms
    @Test
    public void TestCacheReflectMethodsCreateObjects() throws Exception {
        long start = System.currentTimeMillis();

        Class<Student> aClass = (Class<Student>) Class.forName("com.study.entity.Student");
        for (int i = 0; i < count; i++) {
            Student user = aClass.newInstance();
        }

        long end = System.currentTimeMillis();
        System.err.println("反射对象创建耗时：" + (end - start) + "ms");
    }

    //获取全部属性字段 12991ms 13040ms 12985ms
    @Test
    public void TestGetFiled() throws Exception{
        long start = System.currentTimeMillis();

        Class<Student> aClass = (Class<Student>) Class.forName("com.study.entity.Student");
        for (int i = 0; i < count; i++) {
            Field[] fields = aClass.getFields();
        }

        long end = System.currentTimeMillis();
        System.err.println("获取全部属性字段：" + (end - start) + "ms");
    }
    //获取全部属性字段以及属性字段上的注解 44243ms 43112ms
    @Test
    public void TestGetFiledAndAnnotation() throws Exception{
        long start = System.currentTimeMillis();

        Class<Student> aClass = (Class<Student>) Class.forName("com.study.entity.Student");
        for (int i = 0; i < count; i++) {
            Field[] fields = aClass.getFields();
            Arrays.stream(fields).forEach(field ->{
                Annotation[] annotations = field.getAnnotations();
            } );
        }

        long end = System.currentTimeMillis();
        System.err.println("获取全部属性字段以及属性字段上的注解：" + (end - start) + "ms");
    }

    /**********************************************AOP*******************************************/


}
