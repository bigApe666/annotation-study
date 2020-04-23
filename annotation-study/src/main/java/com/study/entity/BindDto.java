package com.study.entity;

import lombok.Data;

/**
 * @author liqinhao
 * @date 2020/4/10 0010 11:48
 */
@Data
public class BindDto {
    private String businessId;
    private String bizType;
    private String resourcePath;
    private Boolean add = true;
}
