package com.lee.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 功能描述: url拦截器
 *
 * @param:
 * @return:
 * @auther: liyiyu
 * @date: 2020/3/17 17:04
 */
public class UrlInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
		//允许跨域,不能放在postHandle内
		response.setHeader("Access-Control-Allow-Credentials", "true");
		String str = request.getHeader("origin");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
		response.setHeader("Access-Control-Max-Age", "0");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,Authorization,WG-App-Version, WG-Device-Id, WG-Network-Type, WG-Vendor, WG-OS-Type, WG-OS-Version, WG-Device-Model, WG-CPU, WG-Sid, WG-App-Id, WG-Token");
		response.setHeader("XDomainRequestAllowed", "1");
		return true;
	}
}
