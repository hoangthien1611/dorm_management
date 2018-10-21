package com.doan.dormmanagement.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegister {
    @NotNull
    @Size(min=6, max=30)
    private String userName;
    @NotNull
    @Size(min=6, max=30)
    private String password;

    private Integer gender;
    @NotNull
    @Size(max=11)
    private String phone;
    @NotNull
    private String address;
    @NotNull
    @Size(min=2, max=50)
    private String fullName;

    @Size(max=9)
    private String mssv;

    @NotNull
    @Size(min=2, max=20)
    private String nameClass;

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

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv= mssv;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
