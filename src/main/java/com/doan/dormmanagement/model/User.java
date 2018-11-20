package com.doan.dormmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    private Integer id;

    private String userName;

    private String password;

    private Integer gender;

    private Integer roleId;

    private Integer status;

    private UserDetail userDetail;

//
//    public List<StudentCode> getStudentCodes() {
//        return studentCodes;
//    }
//
//    public void setStudentCodes(List<StudentCode> studentCodes) {
//        this.studentCodes = studentCodes;
//    }
//
//    private List<StudentCode> studentCodes;
//
//    private List<Notification> notifications;
//
//    public List<Notification> getNotifications() {
//        return notifications;
//    }
//
//    public void setNotifications(List<Notification> notifications) {
//        this.notifications = notifications;
//    }
//
//    public List<StudentCode> getStudentCode() {
//        return studentCodes;
//    }
//
//    public void setStudentCode(List<StudentCode> studentCode) {
//        this.studentCodes = studentCode;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
}
