package com.lee.config;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 *
 * 功能描述: 自定义sessionId获取，用于请求头中传递sessionId，并让shiro获取判断权限
 * 想实现自己的一套session管理器，需继承DefaultWebSessionManager来重写
 *
 * @param:
 * @return:
 * @auther: liyiyu
 * @date: 2020/3/17 17:05
 */
public class MySessionManager extends DefaultWebSessionManager {

    private static final String AUTHORIZATION = "Authorization";

    public MySessionManager() {
        super();
    }


    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //修改shiro管理sessionId的方式，改为获取请求头，前端时header必须带上 Authorization:sessionId
        return WebUtils.toHttp(request).getHeader(AUTHORIZATION);
    }
}
