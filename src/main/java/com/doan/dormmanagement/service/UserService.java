package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    List<User> getAllUsersByRoomId(Integer roomId);

    List<User> getAllUsersByFloorId(Integer floorId);

    List<User> getAllUsersByAreaId(Integer areaId);

    boolean changeStatus(Integer userId, Integer status);
}
