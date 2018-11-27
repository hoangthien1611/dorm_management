package com.doan.dormmanagement.dto;

import com.doan.dormmanagement.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDataResponse extends DataResponse implements Serializable {

    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
