package com.lee.config;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * 功能描述: 统一处理异常返回
 *
 * @param:
 * @return:
 * @auther: liyiyu
 * @date: 2020/3/17 17:06
 */
@ControllerAdvice
public class HandlerException {


    /**
     * 全局异常返回
     *
     * @param e
     * @return
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object globalError(Exception e) {
        return "访问出错！";
    }


    /**
     * Shiro无权限异常返回
     *
     * @param e
     * @return
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public Object unauthorizedError(Exception e) {
        return "无此权限！";
    }


}
