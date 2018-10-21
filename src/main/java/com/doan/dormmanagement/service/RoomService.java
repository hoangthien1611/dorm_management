package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.Room;

import java.util.List;

public interface RoomService {
    List<Room> getRoomsByRoomId(int roomId);

    List<Room> getAllRoomsInFloor(int areaId, int floorId);

    List<Room> getRoomsByAreaId(int areaId);

    boolean addRoom(Room room);
}
