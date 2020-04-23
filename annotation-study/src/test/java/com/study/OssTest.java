package com.study;

import com.study.entity.Student;
import com.study.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @创建时间 2020/4/22.
 * @开发人员 张戮
 */
@SpringBootTest
public class OssTest {

    @Resource
    private StudentService studentService;

    @Test
    public void testAroundAdvertAPI() throws Exception {
        Student student = new Student();
        student.setAge(18);
        student.setHeadImg("小明.jpg");
        student.setStudentId(1L);
        student.setName("多弗朗明哥");
        student.setBackgroundImg("地板.jpg,火车.jpg,风景.jpg");
        studentService.newStudent(student);
    }
}
