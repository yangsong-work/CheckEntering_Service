package com.fri.dao;

import com.fri.model.EnterInfo;

public interface EnterInfoMapper {
    int insert(EnterInfo record);

    int insertSelective(EnterInfo record);
}