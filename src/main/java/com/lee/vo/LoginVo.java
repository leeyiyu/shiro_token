package com.lee.vo;

import java.io.Serializable;

/**
 *
 * 功能描述:登录实体类
 *
 * @param:
 * @return:
 * @auther: liyiyu
 * @date: 2020/3/17 17:01
 */
public class LoginVo implements Serializable {

    private static final long serialVersionUID = 1L;

    //账户
    private String account;

    //密码
    private String password;

    //客户端 1：web 2：app
    private Integer flag;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
