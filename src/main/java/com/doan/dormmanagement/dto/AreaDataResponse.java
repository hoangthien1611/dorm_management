package com.doan.dormmanagement.dto;

import com.doan.dormmanagement.model.Area;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaDataResponse extends DataResponse implements Serializable {

    private List<Area> data;

    public List<Area> getData() {
        return data;
    }

    public void setData(List<Area> data) {
        this.data = data;
    }
}
