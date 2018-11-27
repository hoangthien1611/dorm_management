package com.doan.dormmanagement.dto;

import com.doan.dormmanagement.model.Group;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupDataResponse extends DataResponse implements Serializable {

    private List<Group> data;

    public List<Group> getData() {
        return data;
    }

    public void setData(List<Group> data) {
        this.data = data;
    }
}
