package com.fri.dao;

import com.fri.model.CheckInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckInfoMapper {
    int deleteByPrimaryKey(String cardNumber);

    int insert(CheckInfo record);

    int insertSelective(CheckInfo record);

    CheckInfo selectByPrimaryKey(String cardNumber);

    int updateByPrimaryKeySelective(CheckInfo record);

    int updateByPrimaryKeyWithBLOBs(CheckInfo record);

    int updateByPrimaryKey(CheckInfo record);

    int insertUser(CheckInfo record);
}