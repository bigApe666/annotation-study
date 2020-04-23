package com.study.service;

import com.study.dao.CityDao;
import com.study.entity.City;
import com.study.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @auther zhangHongLu
 * @date 2020/4/22 0022 09:09
 */
@Service
public class StudentService {

    @Autowired
    private CityDao cityDao;

    @Transactional(rollbackFor = Exception.class)
    public void newStudent(Person person) throws Exception {
        City city = City.builder().name("ss").build();
        cityDao.insert(city);
        System.out.println(city.getId());
        //int i=1/0;
    }
}
