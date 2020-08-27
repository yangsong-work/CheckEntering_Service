package com.fri.exception;

import com.alibaba.fastjson.JSON;
import com.fri.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class MyExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    /**
     * 参数检验失败处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public String exceptionHandler(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errMsg = fieldError.getDefaultMessage();
        if (StringUtils.isBlank(errMsg)) {
            errMsg = fieldError.getField() + "不能为空";
        }
        String returnMsg = ResponseUtil.fail("1", errMsg);
        logger.info(returnMsg);
        return returnMsg;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        //拦截http请求方法不允许  ，如：post方法使用get方式
        String error = ex.getMessage();
        logger.error("HttpRequestMethodNotSupportedException {} | 请求方式：{} | 允许方式：{}", error, ex.getMethod(), ex.getSupportedMethods());
        //拦截异常
        logger.error("RuntimeException {} | {} | {} | {}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace(), ex.getMessage());
        String returnMsg = ResponseUtil.fail("1", "请求方法错误");
        logger.info(returnMsg);
        return returnMsg;
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException ex) {
        ex.printStackTrace();
        //拦截异常
        logger.error("RuntimeException {} | {} | {} | {}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace(), ex.getMessage());
        String returnMsg = ResponseUtil.fail("1", "服务器异常");
        logger.info(returnMsg);
        return returnMsg;
    }
}