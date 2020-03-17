package com.lee.shiro;

import com.lee.entity.User;
import com.lee.util.PasswordUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *
 * 功能描述:自定义用户认证授权类
 *
 * @param:
 * @return:
 * @auther: liyiyu
 * @date: 2020/3/17 17:04
 */
@Component
public class MyRealm extends AuthorizingRealm {

    @Value("${password_salt}")
    private String salt;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //伪代码，进入数据库查询拥有的权限查询
        String[] array = {"myName","home"};
        List<String> list = Arrays.asList(array);
        Set<String> set = new HashSet(list);
        info.addStringPermissions(set);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String account = (String) authenticationToken.getPrincipal();  //得到用户名
        String pwd = new String((char[]) authenticationToken.getCredentials()); //得到密码
        String inPasswd = PasswordUtils.entryptPasswordWithSalt(pwd, salt);
        //通过数据库验证账号密码，成功的话返回一个封装的ShiroUser实例
        String saltPasswd = PasswordUtils.entryptPasswordWithSalt("admin", salt);
        User user = null;
        if ("admin".equals(account) && saltPasswd.equals(inPasswd)) {
            user = new User();
            user.setUid("1");
            user.setUname("admin");
            user.setEid(1);
            user.setDeptName("研发部");
            user.setDeptId("1");
            user.setRoleId("1");
            user.setRoleName("java开发工程师");
        }
        if (user != null) {
            //如果身份认证验证成功，返回一个AuthenticationInfo实现；
            return new SimpleAuthenticationInfo(user, pwd, getName());
        } else {
            //错误的帐号
            throw new UnknownAccountException();
        }
    }
}
