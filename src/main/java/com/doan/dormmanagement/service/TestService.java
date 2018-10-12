package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        list.add(new User("Nguyen Hoang Thien", "hoangthien1611@gmail.com", "0123456789"));
        list.add(new User("Nguyen Thien", "hoangthien1611@gmail.com", "0123456789"));
        list.add(new User("Nguyen Hoang", "hoangthien1611@gmail.com", "0123456789"));

        return list;
    }
}
