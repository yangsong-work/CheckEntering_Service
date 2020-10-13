package com.fri.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fri.dao.CheckAddressMapper;
import com.fri.dao.WrongAddressMapper;
import com.fri.model.CheckAddress;
import com.fri.model.WrongAddress;
import com.fri.service.XiChengService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AddressUtil {
    private static Logger log = LoggerFactory.getLogger(AddressUtil.class);
    @Autowired
    XiChengService xiChengService;
    @Autowired
    CheckAddressMapper checkAddressMapper;
    @Autowired
    WrongAddressMapper wrongAddressMapper;

    public List<CheckAddress> checkAddress(String deivceNo,String parentId) {
        List<CheckAddress> dataBaseAddressList = new ArrayList<CheckAddress>();
        //数据库查询结果后返回
        if (parentId==null||"".equals(parentId)) {
            dataBaseAddressList = checkAddressMapper.selectParentLevel();
        } else {
            dataBaseAddressList = checkAddressMapper.selectForLevelByParentId(parentId);
        }
        if (dataBaseAddressList != null && !dataBaseAddressList.isEmpty()) {
            log.info("数据库查询核查地理信息{}", dataBaseAddressList);
            return dataBaseAddressList;
        }
        //西城接口查询返回
        List<CheckAddress> returnList = new ArrayList<>();

        String data = xiChengService.checkAddress(deivceNo,parentId);

        // 测试代码
        //  test1 data2 = (test1) test1Mapper.selectjson();
        //  String data = (String) data2.getTtt();
        //  System.out.println(data);

        Map<String, Object> map = JSON.parseObject(data, Map.class);
        //查询失败直接返回空list
        if (map == null || !(map.get("status").toString().equals("0"))) {
            return returnList;
        }
        JSONObject o = JSON.parseObject(data);
        //查询成功，因为数据结构不同，分一级二级处理 和 三级处理
        if (parentId==null||parentId.equals("")) {
            //一级二级处理 返回一级 入库一级二级
            //获取一级list
            List<CheckAddress> firstCheckAddressList = JSON.parseArray(o.getString("results"), CheckAddress.class);
            handleAddress(firstCheckAddressList, 1);
            //遍历二级list
            List<JSONObject> list = (List<JSONObject>) JSON.parseObject(data, JSONObject.class).get("results");
            List<CheckAddress> subCheckAddressList = new ArrayList<>();
            for (JSONObject object : list) {
                subCheckAddressList.addAll(JSON.parseArray(object.getString("children"), CheckAddress.class));
                handleAddress(subCheckAddressList, 2);
            }
            if (firstCheckAddressList == null || firstCheckAddressList.isEmpty() || subCheckAddressList == null || subCheckAddressList.isEmpty()) {
                return firstCheckAddressList;
            }
            try {
                returnList.addAll(firstCheckAddressList);
                returnList.addAll(subCheckAddressList);
                checkAddressMapper.insertBatch(returnList);
                log.info("一级核查地地理信息入库成功{}", firstCheckAddressList);
                log.info("二级核查地地理信息入库成功{}", subCheckAddressList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //
            return firstCheckAddressList;
        } else {
            //三级处理 返回三级 入库三级

            returnList = JSON.parseArray(o.getString("results"), CheckAddress.class);


            if (returnList == null || returnList.isEmpty()) {
                return returnList;
            }
            //求情三级地理信息，入库后返回
            try {
                handleAddress(returnList, 3);
                checkAddressMapper.insertBatch(returnList);
                log.info("三级核查地地理信息入库成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnList;

        }


    }


    public void handleAddress(List<CheckAddress> list, Integer level) {
        //指定level
        for (CheckAddress address : list) {

            //三级地理信息位置改变错误处理
            if(level==3){
                List<WrongAddress> wrongAddressList = wrongAddressMapper.selectAddress();
                   for(WrongAddress wrongAddress:wrongAddressList){
                       if(address.getValue().contains(wrongAddress.getKeyWord())){
                           address.setValue(wrongAddress.getLevel3Address());
                       }
                   }
            }

            address.setLevel(level);
        }

    }
}
