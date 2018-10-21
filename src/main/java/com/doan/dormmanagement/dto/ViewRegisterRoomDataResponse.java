package com.doan.dormmanagement.dto;

import com.doan.dormmanagement.model.ViewRegisterRoom;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ViewRegisterRoomDataResponse extends DataResponse implements Serializable {

    private List<ViewRegisterRoom> data;

    public List<ViewRegisterRoom> getData() {
        return data;
    }

    public void setData(List<ViewRegisterRoom> data) {
        this.data = data;
    }
}
