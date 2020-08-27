package com.fri.dao;

import com.fri.model.CheckWarnInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckWarnInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CheckWarnInfo record);

    int insertSelective(CheckWarnInfo record);

    CheckWarnInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CheckWarnInfo record);

    int updateByPrimaryKey(CheckWarnInfo record);

    List<CheckWarnInfo> selectByCardNumber(String cardNumber);

    int deleteByCardNumber(String cardNumber);

    int insertBatch(List<CheckWarnInfo> list);
}