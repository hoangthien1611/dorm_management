package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.model.User;
import com.doan.dormmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/user/index";
    }

    @GetMapping("/add")
    public String add() {
        return "admin/user/add";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public List<User> getUsersByRoomId(@PathVariable("roomId") Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer roomId = Integer.parseInt(id.get());
                List<User> list = userService.getAllUsersByRoomId(roomId);
                return list != null ? list : new ArrayList<>();
            }
            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }
}
