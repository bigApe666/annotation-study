package com.study.entity;

import com.study.annotation.*;
import lombok.Data;

/**
 * @auther zhangHongLu
 * @date 2020/4/22 0022 08:07
 */
@Data
@OssService
public class Student extends Person{
    @OssBusinessId
    private Long studentId;

    private String name;

    private Integer age;

    @OssPicture(bizType = "headImg",format =1)
    private String headImg;

    @OssPicture(bizType = "backgroundImg",format =1)
    private String backgroundImg;
}
