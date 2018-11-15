package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.RegisterRoom;

import java.util.List;

public interface RegisterRoomService {
    List<RegisterRoom> getAllByRoomId(Integer roomId);

    List<RegisterRoom> getAllByAreaId(Integer areaId);
}
