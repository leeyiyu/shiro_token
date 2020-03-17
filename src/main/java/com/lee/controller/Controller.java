package com.lee.controller;

import com.lee.entity.User;
import com.lee.vo.LoginVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {


    @PostMapping("/login")
    public Object login(@RequestBody LoginVo loginVo) {
        //得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginVo.getAccount(), loginVo.getPassword());
        token.setRememberMe(false);
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        try {
            //登录，即身份验证
            subject.login(token);
            Session session = subject.getSession();
            User user = User.loginUser();
            user.setFlag(loginVo.getFlag());
            user.setSessionId(session.getId());
            //返回一个sessionId
            return user;
        } catch (UnknownAccountException e){
            return "账号/密码错误";
        }
        catch (AuthenticationException e) {
            //身份验证失败
            return "程序错误";
        }
    }

    @PostMapping("/logout")
    public Object logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return "成功退出登录！";
        } catch (Exception e) {
            return "退出登录失败！";
        }
    }


    @GetMapping("/poetry1")
    @RequiresPermissions("poetry1")
    public Object poetry1(){
        return "床前明月光";
    }

    @GetMapping("/poetry2")
    @RequiresPermissions("poetry2")
    public Object poetry2(){
        return "疑是地上霜";
    }

    @GetMapping("/poetry3")
    @RequiresPermissions("poetry3")
    public Object poetry3(){
        return "举头望明月";
    }

    @GetMapping("/poetry4")
    @RequiresPermissions("poetry4")
    public Object poetry4(){
        return "低头思故乡";
    }



}
