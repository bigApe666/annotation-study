package com.study.entity;

import com.study.annotation.OssBusinessId;
import com.study.annotation.OssPicture;
import com.study.annotation.OssService;
import lombok.*;

/**
 * @auther zhangHongLu
 * @date 2020/4/23 0023 14:16
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@OssService
public class City {
    @OssBusinessId
    private String id;
    @OssPicture(bizType = "myTest",format = 1)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
