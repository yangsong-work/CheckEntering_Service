package com.fri.dao;

import com.fri.model.EnterInfo;

public interface EnterInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EnterInfo record);

    int insertSelective(EnterInfo record);

    EnterInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EnterInfo record);

    int updateByPrimaryKey(EnterInfo record);
}