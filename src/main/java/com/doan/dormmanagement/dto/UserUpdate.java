package com.doan.dormmanagement.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserUpdate {
    private Integer userId;
    private String userName;
    @NotNull
    private Integer gender;
    @NotNull
    @Size(max=11)
    private String phone;
    @NotNull
    private String address;
    @NotNull
    @Size(min=2, max=50)
    private String fullName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
