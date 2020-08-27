package com.fri.checkenteringservice;

import com.fri.pojo.bo.xicheng.response.BaseResponse;
import com.fri.pojo.bo.xicheng.response.CheckPersonPhotoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CheckenteringServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void printResult() {
        String jsondata = "{ status:0,msg:'',checkinfoid：'xxxxxxxxxxxxxxxxxxxx'}";
        BaseResponse baseResponse = new BaseResponse();
        CheckPersonPhotoResponse req = new CheckPersonPhotoResponse();
        req.setZp("1111111");
//        req.setMsg("msg");
//        req.setStatus(0);
        System.out.println(req.toString());
    }
}
