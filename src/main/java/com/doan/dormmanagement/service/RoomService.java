package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.Room;

import java.util.List;

public interface RoomService {
    List<Room> getRoomsByAreaId(int areaId);

    List<Room> getRoomsByFloorId(int floorId);

    boolean addRoom(Room room);

    boolean editRoom(Room room);
}
