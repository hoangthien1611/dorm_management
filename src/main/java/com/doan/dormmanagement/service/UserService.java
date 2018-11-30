package com.doan.dormmanagement.service;

import com.doan.dormmanagement.dto.PasswordChange;
import com.doan.dormmanagement.dto.UserLogin;
import com.doan.dormmanagement.dto.UserRegister;
import com.doan.dormmanagement.dto.UserUpdate;
import com.doan.dormmanagement.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    List<User> getAllUsersByRoomId(Integer roomId);

    List<User> getAllUsersByFloorId(Integer floorId);

    List<User> getAllUsersByAreaId(Integer areaId);

    List<User> getAllUsersByGroupId(Integer groupId);

    User getUserByUserId(Integer userId);

    User getUserByUsername(String username);

    boolean changeStatus(Integer userId, Integer status);

    boolean addUser(UserRegister user);

    boolean resetPassword(PasswordChange passwordDto);

    boolean changePassword(PasswordChange passwordChange);

    boolean isUserNameExisted(String username);

    boolean changeGroup(Integer userId, Integer groupId);

    boolean updateInfor(UserUpdate userUpdate);

    boolean login(UserLogin userLogin);
}
