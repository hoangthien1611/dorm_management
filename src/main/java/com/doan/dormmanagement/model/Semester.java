package com.doan.dormmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Semester implements Serializable {

    private Integer id;
    @NotNull
    private String name;
    @NotNull
    @Min(1)
    private Integer month;

    private Integer monthBegin;

    private Integer monthEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getMonthBegin() {
        return monthBegin;
    }

    public void setMonthBegin(Integer monthBegin) {
        this.monthBegin = monthBegin;
    }

    public Integer getMonthEnd() {
        return monthEnd;
    }

    public void setMonthEnd(Integer monthEnd) {
        this.monthEnd = monthEnd;
    }
}
