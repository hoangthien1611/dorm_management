package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.service.AreaService;
import com.doan.dormmanagement.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/room")
public class AdminRoomController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/area/{id}")
    public String index(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("rooms", roomService.getAllRooms(id));
        model.addAttribute("areas", areaService.getAllAreas());
        return "admin/room/index";
    }
}
