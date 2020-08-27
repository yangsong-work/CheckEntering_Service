package com.fri.dao;

import com.fri.model.CheckImage;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CheckImage record);

    int insertSelective(CheckImage record);

    CheckImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CheckImage record);

    int updateByPrimaryKeyWithBLOBs(CheckImage record);

    int updateByPrimaryKey(CheckImage record);
}