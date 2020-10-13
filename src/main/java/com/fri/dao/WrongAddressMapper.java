package com.fri.dao;

import com.fri.model.WrongAddress;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WrongAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WrongAddress record);

    int insertSelective(WrongAddress record);

    WrongAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WrongAddress record);

    int updateByPrimaryKey(WrongAddress record);

    List<WrongAddress> selectAddress();
}