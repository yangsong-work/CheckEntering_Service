package com.fri.controller;

import com.fri.dao.CheckAddressMapper;
import com.fri.dao.WrongAddressMapper;
import com.fri.exception.NoMessageException;
import com.fri.model.CheckAddress;
import com.fri.model.WrongAddress;
import com.fri.pojo.bo.pinen.VerifyIDCardRequest;
import com.fri.service.APPService;
import com.fri.service.CheckEnterService;
import com.fri.utils.UserUtil;
import com.fri.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Deprecated
public class TestController {
    @Autowired
    CheckEnterService checkEnterService;
    @Autowired
    APPService appService;
    @Autowired
    WrongAddressMapper wrongAddressMapper;
    @Autowired
    CheckAddressMapper checkAddressMapper;
    @RequestMapping("/11")
    public String getm() throws NoMessageException {
        System.out.println("aaa");
        if(true) {
            throw new NoMessageException();
        }
        return "1";
    }

    @RequestMapping("/socketest")
    public ModelAndView houru() {
        ModelAndView modelAndView = new ModelAndView("/hello");//设置对应JSP的模板文件
        modelAndView.addObject("name", "miyue");
        return modelAndView;
    }

//    @RequestMapping("/testpinen")
//    public String testpinen() {
//        Map map = appService.notifyLogin("123456");
//        System.out.println(map);
//        return "111";
//    }
@RequestMapping("/test")
public String run() {
    List<WrongAddress> wrongAddressList = wrongAddressMapper.selectAddress();
    for(WrongAddress wrongAddress:wrongAddressList){
        CheckAddress checkAddress = new CheckAddress();
        checkAddress.setId(wrongAddress.getAddressId());
        checkAddress.setValue(wrongAddress.getLevel3Address());
        int i = checkAddressMapper.updateByPrimaryKeySelective(checkAddress);
    }
        return "a";
}


}
