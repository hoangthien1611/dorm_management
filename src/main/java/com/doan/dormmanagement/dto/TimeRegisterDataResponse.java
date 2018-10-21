package com.doan.dormmanagement.dto;

import com.doan.dormmanagement.model.TimeRegister;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeRegisterDataResponse extends DataResponse implements Serializable {

    private TimeRegister data;

    public TimeRegister getData() {
        return data;
    }

    public void setData(TimeRegister data) {
        this.data = data;
    }

}
