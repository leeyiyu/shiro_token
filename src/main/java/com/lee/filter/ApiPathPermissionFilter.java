package com.lee.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 功能描述: 自定义拦截
 *
 * @param:
 * @return:
 * @auther: liyiyu
 * @date: 2020/3/17 17:04
 */
public class ApiPathPermissionFilter extends PathMatchingFilter {


    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        //跨域问题
        if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            // 如果没有登录, 直接返回未登录状态，由前端进行跳转到登录页面
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.getWriter().write("{\"status\":" + HttpServletResponse.SC_UNAUTHORIZED + ",\"msg\":\"会话失效，请重新登录!\"}");


            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin",((HttpServletRequest) request).getHeader("origin"));
            httpResponse.setHeader("Cache-Control", "no-cache");
            httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
            httpResponse.setHeader("Access-Control-Max-Age", "0");
            httpResponse.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, authorization, " +
                    "WG-App-Version, WG-Device-Id, WG-Network-Type, WG-Vendor, WG-OS-Type, WG-OS-Version, WG-Device-Model, WG-CPU, WG-Sid, WG-App-Id, WG-Token");
            httpResponse.setHeader("XDomainRequestAllowed", "1");
            return false;
        } else {
            return true;
        }
    }
}
