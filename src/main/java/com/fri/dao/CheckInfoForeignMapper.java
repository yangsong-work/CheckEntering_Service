package com.fri.dao;

import com.fri.model.CheckInfoForeign;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckInfoForeignMapper {
    int deleteByPrimaryKey(String cardNumber);

    int insert(CheckInfoForeign record);

    int insertSelective(CheckInfoForeign record);

    CheckInfoForeign selectByPrimaryKey(String cardNumber);

    int updateByPrimaryKeySelective(CheckInfoForeign record);

    int updateByPrimaryKey(CheckInfoForeign record);

    int insertUser(CheckInfoForeign record);
}