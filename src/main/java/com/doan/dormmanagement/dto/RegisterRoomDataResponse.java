package com.doan.dormmanagement.dto;

import com.doan.dormmanagement.model.RegisterRoom;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRoomDataResponse extends DataResponse implements Serializable {

    private List<RegisterRoom> data;

    public List<RegisterRoom> getData() {
        return data;
    }

    public void setData(List<RegisterRoom> data) {
        this.data = data;
    }
}
