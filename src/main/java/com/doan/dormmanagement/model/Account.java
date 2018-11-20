package com.doan.dormmanagement.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private String userName;
    private String password;
    private Integer gender;

    public Account(String userName, String password, Integer gender) {
        this.userName = userName;
        this.password = password;
        this.gender = gender;
    }
}
