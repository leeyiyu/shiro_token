package com.lee.config;

import com.lee.interceptor.UrlInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * 功能描述: MVC拦截器配置
 *
 * @param:
 * @return:
 * @auther: liyiyu
 * @date: 2020/3/17 17:06
 */
@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //为所有请求处理跨域问题
        registry.addInterceptor(new UrlInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
