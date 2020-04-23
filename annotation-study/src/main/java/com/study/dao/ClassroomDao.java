package com.study.dao;

import com.study.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClassroomDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Classroom record);

    int insertSelective(Classroom record);

    Classroom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Classroom record);

    int updateByPrimaryKey(Classroom record);
}