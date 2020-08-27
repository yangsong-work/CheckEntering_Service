package com.fri.dao;

import com.fri.model.Test1;
import org.springframework.stereotype.Repository;

@Repository
public interface Test1Mapper {
    int insert(Test1 record);

    int insertSelective(Test1 record);

    Test1 select1(String key);
}