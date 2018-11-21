package com.doan.dormmanagement.model;

import com.doan.dormmanagement.utility.TimeString;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRoom implements Serializable {

    private Integer id;

    private Integer userId;

    private String fullName;

    private Integer gender;

    private Integer number;

    private String className;

    private String studentCode;

    private String year;

    private Integer status;

    private Timestamp timeCensor;

    private Timestamp timeRegister;

    private Integer semesterId;

    private String semesterName;

    private Integer roomId;

    private String roomName;

    private Integer floorId;

    private String floorName;

    private Integer areaId;

    private String areaName;

    private String timeRegisterCustom;

    private String timeCensorCustom;

    public String getTimeRegisterCustom() {
        return TimeString.convertTimeStampToTimeString(timeRegister);
    }

    public void setTimeRegisterCustom(String timeRegisterCustom) {
        this.timeRegisterCustom = timeRegisterCustom;
    }

    public String getTimeCensorCustom() {
        return TimeString.convertTimeStampToTimeString(timeCensor);
    }

    public void setTimeCensorCustom(String timeCensorCustom) {
        this.timeCensorCustom = timeCensorCustom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
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

    public Timestamp getTimeCensor() {
        return timeCensor;
    }

    public void setTimeCensor(Timestamp timeCensor) {
        this.timeCensor = timeCensor;
    }

    public Timestamp getTimeRegister() {
        return timeRegister;
    }

    public void setTimeRegister(Timestamp timeRegister) {
        this.timeRegister = timeRegister;
    }

    public Integer getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

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
}
