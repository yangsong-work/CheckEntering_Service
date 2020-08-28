package com.fri.dao;

import com.fri.model.CountryInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryInfoMapper {
    int insert(CountryInfo record);

    int insertSelective(CountryInfo record);

    String selectCountryEn(String countryCn);
}