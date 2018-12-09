package com.doan.dormmanagement.dto;

import com.doan.dormmanagement.model.InfoIndex;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoIndexDataResponse extends DataResponse implements Serializable {

    private InfoIndex data;

    public InfoIndex getData() {
        return data;
    }

    public void setData(InfoIndex data) {
        this.data = data;
    }
}
