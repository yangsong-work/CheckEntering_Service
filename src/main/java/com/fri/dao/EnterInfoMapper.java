package com.fri.dao;

import com.fri.model.EnterInfoWithBLOBs;

public interface EnterInfoMapper {
    int insert(EnterInfoWithBLOBs record);

    int insertSelective(EnterInfoWithBLOBs record);
}