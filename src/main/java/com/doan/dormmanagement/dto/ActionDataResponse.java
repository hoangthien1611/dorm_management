package com.doan.dormmanagement.dto;

import com.doan.dormmanagement.model.Action;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionDataResponse extends DataResponse implements Serializable {

    private List<Action> data;

    public List<Action> getData() {
        return data;
    }

    public void setData(List<Action> data) {
        this.data = data;
    }
}
