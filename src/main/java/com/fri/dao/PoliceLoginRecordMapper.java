package com.fri.dao;

import com.fri.model.PoliceLoginRecord;
import com.fri.pojo.bo.app.request.CheckOptionRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliceLoginRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PoliceLoginRecord record);

    int insertSelective(PoliceLoginRecord record);

    PoliceLoginRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PoliceLoginRecord record);

    int updateByPrimaryKey(PoliceLoginRecord record);

    int updateByPadIdDeviceNo(PoliceLoginRecord policeLoginRecord);

    int updateCheckOption(CheckOptionRequest checkOptionRequest);

    String selectDeviceNoByPadId(String padId);

    int updateVerifyScore(String padId,String deviceNo,@Param("deviceScore") String verifyScore);
}