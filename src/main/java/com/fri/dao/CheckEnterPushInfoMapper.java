package com.fri.dao;

import com.fri.model.CheckEnterPushInfo;
import com.fri.model.CheckEnterPushInfoKey;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckEnterPushInfoMapper {
    int deleteByPrimaryKey(CheckEnterPushInfoKey key);

    int insert(CheckEnterPushInfo record);

    int insertSelective(CheckEnterPushInfo record);

    CheckEnterPushInfo selectByPrimaryKey(CheckEnterPushInfoKey key);

    int updateByPrimaryKeySelective(CheckEnterPushInfo record);

    int updateByPrimaryKey(CheckEnterPushInfo record);

    int updateRecord(CheckEnterPushInfo record);
}