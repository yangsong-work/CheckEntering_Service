package com.fri.dao;

import com.fri.model.EnterInfoWithBLOBs;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterInfoMapper {
    int insert(EnterInfoWithBLOBs record);

    int insertSelective(EnterInfoWithBLOBs record);
}