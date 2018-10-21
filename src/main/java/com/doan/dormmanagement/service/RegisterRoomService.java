package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.RegisterRoom;

import com.doan.dormmanagement.model.ViewRegisterRoom;

import java.util.List;

public interface RegisterRoomService {
    List<ViewRegisterRoom> getRegRoomByUserId(Integer userId);

    boolean addRegisterRoom(RegisterRoom registerRoom);

    boolean delRegRoom(Integer regId);

    List<RegisterRoom> getAllByRoomId(Integer roomId);

    List<RegisterRoom> getAllByAreaId(Integer areaId);

    List<RegisterRoom> getAllByFloorId(Integer floorId);

    boolean acceptAll(Integer[] intArr);
}
