package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.User;
import com.doan.dormmanagement.model.UserRegister;

import java.util.List;

public interface UserService {

    boolean addStudent(UserRegister user);
    List<User> getAllUsers();
    List<User> getUsersbyRoomId(int roomId);
    User getUserByCode(String code);
    User getUserByUsername(String username);
}
