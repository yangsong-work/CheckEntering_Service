package com.fri.dao;

import com.fri.model.PoliceInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliceInfoMapper {
    int insert(PoliceInfo record);

    int insertSelective(PoliceInfo record);

    PoliceInfo selectName(String userIdCard);

    PoliceInfo selectByAccount(String userAccount);
}