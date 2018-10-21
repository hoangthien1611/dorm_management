package com.doan.dormmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRoom implements Serializable {
    private Integer userId;

    private Integer number;

    private String year;

    private Integer status;

    private String timeCensor;

    private String timeRegister;

    private Integer semesterId;

    private Integer roomId;

//    private String timeRegisterCustom;
//
//    private String timeCensorCustom;
//
//    public String getTimeRegisterCustom() {
//        return TimeString.convertTimeStampToTimeString(timeRegister);
//    }
//
//    public void setTimeRegisterCustom(String timeRegisterCustom) {
//        this.timeRegisterCustom = timeRegisterCustom;
//    }
//
//    public String getTimeCensorCustom() {
//        return TimeString.convertTimeStampToTimeString(timeCensor);
//    }
//
//    public void setTimeCensorCustom(String timeCensorCustom) {
//        this.timeCensorCustom = timeCensorCustom;
//    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTimeCensor() {
        return timeCensor;
    }

    public void setTimeCensor(String timeCensor) {
        this.timeCensor = timeCensor;
    }

    public String getTimeRegister() {
        return timeRegister;
    }

    public void setTimeRegister(String timeRegister) {
        this.timeRegister = timeRegister;
    }

    public Integer getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
