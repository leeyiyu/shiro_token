package com.lee.config;


import com.lee.filter.ApiPathPermissionFilter;
import com.lee.shiro.MyRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * 功能描述: shiro配置类
 *
 * @param:
 * @return:
 * @auther: liyiyu
 * @date: 2020/3/17 17:05
 */
@Configuration
public class ShiroConfig {

    /**
     * 设置/login /logout 两个请求可以任意访问
     */
    @Bean
    public ShiroFilterFactoryBean factory(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        factoryBean.setSecurityManager(securityManager);

        // 自定义拦截器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("apiPathPermissionFilter", new ApiPathPermissionFilter());
        factoryBean.setFilters(filterMap);

        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterRuleMap.put("/logout", "logout");
        // 配置不会被拦截的链接 顺序判断
        filterRuleMap.put("/login", "anon");
        // 其他请求通过我们自己的apiPathPermissionFilter
        filterRuleMap.put("/*", "apiPathPermissionFilter");
        filterRuleMap.put("/**", "apiPathPermissionFilter");
        filterRuleMap.put("/*.*", "apiPathPermissionFilter");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);

        return factoryBean;
    }

    /**
     * SecurityManager安全管理器,是Shiro的核心
     */
    @Bean
    public SecurityManager securityManager(MyRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置session生命周期
        securityManager.setSessionManager(sessionManager());
        // 设置自定义 realm
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 自定义sessionManager
     */
    @Bean
    public SessionManager sessionManager() {
        //由shiro管理session，每次访问后会重置过期时间
        MySessionManager mySessionManager = new MySessionManager();
        //设置过期时间，单位：毫秒
        mySessionManager.setGlobalSessionTimeout(MySessionManager.DEFAULT_GLOBAL_SESSION_TIMEOUT);
        return mySessionManager;
    }


    /**
     * LifecycleBeanPostProcessor将Initializable和Destroyable的实现类统一在其内部,
     * 自动分别调用了Initializable.init()和Destroyable.destroy()方法,从而达到管理shiro bean生命周期的目的
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


}
