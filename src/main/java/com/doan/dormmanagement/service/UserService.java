package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.User;

import java.util.List;

public interface UserService {

    boolean getLogin(User a);
    List<User> getAllUsers();
    List<User> getUsersbyRoomId(int roomId);
}
