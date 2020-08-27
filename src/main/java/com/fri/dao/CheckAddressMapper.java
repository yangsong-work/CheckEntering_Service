package com.fri.dao;

import com.fri.model.CheckAddress;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckAddressMapper {
    int deleteByPrimaryKey(String id);

    int insert(CheckAddress record);

    int insertSelective(CheckAddress record);

    CheckAddress selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CheckAddress record);

    int updateByPrimaryKey(CheckAddress record);

    List<CheckAddress> selectForLevelByParentId(String parentId);

    List<CheckAddress> selectParentLevel();

    int insertBatch(List<CheckAddress> list);

}