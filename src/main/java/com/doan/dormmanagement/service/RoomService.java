package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.Room;

import java.util.List;

public interface RoomService {
    List<Room> getAllRooms(int areaId);
}
