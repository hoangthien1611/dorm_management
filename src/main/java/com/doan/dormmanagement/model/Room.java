package com.doan.dormmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Room implements Serializable {
    private Integer id;
    @NotNull
    private String name;

    private Integer areaId;

    private Integer floorId;

    private Integer costId;

    private Integer functionId;
    @NotNull
    @Min(1)
    private Integer numberBed;

    private Integer gender;

    private Integer status;
    @NotNull
    @Min(1)
    private Integer studentMax;

    private Integer studentPresent;

    private Integer studentRegister;

    private String name_floor;

    private String name_function;

    private String name_area;

    private String name_cost;

    private Float value_cost;

    private Integer level_cost;

    public String getName_floor() {
        return name_floor;
    }

    public void setName_floor(String name_floor) {
        this.name_floor = name_floor;
    }

    public String getName_function() {
        return name_function;
    }

    public void setName_function(String name_function) {
        this.name_function = name_function;
    }

    public String getName_area() {
        return name_area;
    }

    public void setName_area(String name_area) {
        this.name_area = name_area;
    }

    public String getName_cost() {
        return name_cost;
    }

    public void setName_cost(String name_cost) {
        this.name_cost = name_cost;
    }

    public Float getValue_cost() {
        return value_cost;
    }

    public void setValue_cost(Float value_cost) {
        this.value_cost = value_cost;
    }

    public Integer getLevel_cost() {
        return level_cost;
    }

    public void setLevel_cost(Integer level_cost) {
        this.level_cost = level_cost;
    }

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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public Integer getCostId() {
        return costId;
    }

    public void setCostId(Integer costId) {
        this.costId = costId;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Integer getNumberBed() {
        return numberBed;
    }

    public void setNumberBed(Integer numberBed) {
        this.numberBed = numberBed;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStudentMax() {
        return studentMax;
    }

    public void setStudentMax(Integer studentMax) {
        this.studentMax = studentMax;
    }

    public Integer getStudentPresent() {
        return studentPresent;
    }

    public void setStudentPresent(Integer studentPresent) {
        this.studentPresent = studentPresent;
    }

    public Integer getStudentRegister() {
        return studentRegister;
    }

    public void setStudentRegister(Integer studentRegister) {
        this.studentRegister = studentRegister;
    }
}
