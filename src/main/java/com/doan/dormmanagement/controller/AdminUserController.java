package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.model.Area;
import com.doan.dormmanagement.model.User;
import com.doan.dormmanagement.service.AreaService;
import com.doan.dormmanagement.service.FloorService;
import com.doan.dormmanagement.service.RoomService;
import com.doan.dormmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private FloorService floorService;

    @GetMapping("/student")
    public String indexUser(Model model) {
        List<Area> areas = areaService.getAllAreas();
        int firstAreaId = areas.size() > 0 ? areas.get(0).getId() : 0;
        model.addAttribute("areas", areas);
        model.addAttribute("floors", floorService.getAllFloorsByAreaId(firstAreaId));
        model.addAttribute("rooms", roomService.getRoomsByAreaId(firstAreaId));
        model.addAttribute("users", userService.getAllUsers());
        return "admin/user/student";
    }

    @GetMapping("/add")
    public String add() {
        return "admin/user/add";
    }

    @GetMapping("/student/room/{roomId}")
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

    @GetMapping("/student/floor/{floorId}")
    @ResponseBody
    public List<User> getUsersByFloorId(@PathVariable("floorId") Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer floorId = Integer.parseInt(id.get());
                List<User> list = userService.getAllUsersByFloorId(floorId);
                return list != null ? list : new ArrayList<>();
            }
            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @GetMapping("/student/area/{areaId}")
    @ResponseBody
    public List<User> getUsersByAreaId(@PathVariable("areaId") Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer areaId = Integer.parseInt(id.get());
                List<User> list = userService.getAllUsersByAreaId(areaId);
                return list != null ? list : new ArrayList<>();
            }
            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @PostMapping("/user/change-status")
    @ResponseBody
    public String changeStatus(@RequestParam("userId") Integer id, @RequestParam("stt") Integer stt) {
        if (userService.changeStatus(id, stt)) {
            return "OK";
        }
        return null;
    }
}
