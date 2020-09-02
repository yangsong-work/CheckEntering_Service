package com.fri.dao;

import com.fri.model.CheckPeople;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckPeopleMapper {
    int insertPeople(CheckPeople checkPeople);

    Integer selectAllCount(Integer id);

    Integer selectRzCount(Integer id);

    Integer selectGreenCount(Integer id);

    Integer selectYellowCount(Integer id);

    Integer selectRedCount(Integer id);

    Integer selectWarnCount(Integer id);

    List<String> selectAllIdCard(Integer id);

    List<String> selectRzIdCard(Integer id);

    List<String> selectGreenIdCard(Integer id);

    List<String> selectYellowIdCard(Integer id);

    List<String> selectRedIdCard(Integer id);

    List<String> selectWarnIdCard(Integer id);
}
