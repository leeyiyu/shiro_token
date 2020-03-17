package com.lee.entity;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.io.Serializable;

/**
 * 功能描述: 封装的用户类
 *
 * @auther: liyiyu
 * @date: 2019/9/16 15:08
 */
public class User implements Serializable {

    //员工id
    private String uid;
    //员工姓名
    private String uname;
    //企业id
    private Integer eid;
    //部门id
    private String deptId;
    //web端：0 手机端：1
    private Integer flag;
    //部门名称
    private String deptName;
    //职务id
    private String roleId;
    //职务名称
    private String roleName;
    //用户sessionId
    private Serializable sessionId;

    public static User loginUser() {
        Subject subject = SecurityUtils.getSubject();
        User shiroUser = (User) subject.getPrincipal();
        return shiroUser;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Serializable getSessionId() {
        return sessionId;
    }

    public void setSessionId(Serializable sessionId) {
        this.sessionId = sessionId;
    }
}
