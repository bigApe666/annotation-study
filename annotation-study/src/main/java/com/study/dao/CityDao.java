package com.study.dao;

import com.study.annotation.OssBindMapping;
import com.study.constant.OperateType;
import com.study.entity.City;


/**
 * @auther zhangHongLu
 * @date 2020/4/23 0023 14:17
 */
public interface CityDao {
    @OssBindMapping(operate = OperateType.ADD)
    int insert(City city);
}
