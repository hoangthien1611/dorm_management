package com.doan.dormmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoIndex implements Serializable {
    Integer sumRoom;
    Integer sumArea;
    Integer sumUser;
    HashMap<Integer, Integer> sumGroup;
    Integer totalGroup;

    public Integer getTotalGroup() {
        return sumGroup != null ? sumGroup.size() : 0;
    }

    public void setTotalGroup(Integer totalGroup) {
        this.totalGroup = totalGroup;
    }

    public Integer getSumRoom() {
        return sumRoom;
    }

    public void setSumRoom(Integer sumRoom) {
        this.sumRoom = sumRoom;
    }

    public Integer getSumArea() {
        return sumArea;
    }

    public void setSumArea(Integer sumArea) {
        this.sumArea = sumArea;
    }

    public Integer getSumUser() {
        return sumUser;
    }

    public void setSumUser(Integer sumUser) {
        this.sumUser = sumUser;
    }

    public HashMap<Integer, Integer> getSumGroup() {
        return sumGroup;
    }

    public void setSumGroup(HashMap<Integer, Integer> sumGroup) {
        this.sumGroup = sumGroup;
    }
}
