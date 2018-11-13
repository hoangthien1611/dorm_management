package com.doan.dormmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubsistenceFee implements Serializable {
    private Integer id;
    @NotNull
    @Min(1)
    @Max(12)
    private Integer month;
    @NotNull
    @Min(1)
    private Integer year;

    private Float total;

    private Integer levelWater;

    private Integer levelElec;

    private  Integer status;
    @NotNull
    @Min(1)
    private Integer newNumberWater;
    @NotNull
    @Min(1)
    private Integer newNumberElec;
    @NotNull
    @Min(1)
    private Integer oldNumberWater;
    @NotNull
    @Min(1)
    private Integer oldNumberElec;

    private Float costWater;

    private Float costElec;

    private Integer roomId;

    private String roomName;

    private Integer floorId;

    private String floorName;

    private Integer areaId;

    private String areaName;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Integer getLevelWater() {
        return levelWater;
    }

    public void setLevelWater(Integer levelWater) {
        this.levelWater = levelWater;
    }

    public Integer getLevelElec() {
        return levelElec;
    }

    public void setLevelElec(Integer levelElec) {
        this.levelElec = levelElec;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNewNumberWater() {
        return newNumberWater;
    }

    public void setNewNumberWater(Integer newNumberWater) {
        this.newNumberWater = newNumberWater;
    }

    public Integer getNewNumberElec() {
        return newNumberElec;
    }

    public void setNewNumberElec(Integer newNumberElec) {
        this.newNumberElec = newNumberElec;
    }

    public Integer getOldNumberWater() {
        return oldNumberWater;
    }

    public void setOldNumberWater(Integer oldNumberWater) {
        this.oldNumberWater = oldNumberWater;
    }

    public Integer getOldNumberElec() {
        return oldNumberElec;
    }

    public void setOldNumberElec(Integer oldNumberElec) {
        this.oldNumberElec = oldNumberElec;
    }

    public Float getCostWater() {
        return costWater;
    }

    public void setCostWater(Float costWater) {
        this.costWater = costWater;
    }

    public Float getCostElec() {
        return costElec;
    }

    public void setCostElec(Float costElec) {
        this.costElec = costElec;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
