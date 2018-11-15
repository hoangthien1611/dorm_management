package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.model.Area;
import com.doan.dormmanagement.service.AreaService;
import com.doan.dormmanagement.service.FloorService;
import com.doan.dormmanagement.service.RegisterRoomService;
import com.doan.dormmanagement.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/register-room")
public class AdminRegisterRoomController {

    @Autowired
    private RegisterRoomService registerRoomService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private FloorService floorService;

    @GetMapping()
    public String index(Model model) {
        List<Area> areas = areaService.getAllAreas();
        int firstAreaId = areas.size() > 0 ? areas.get(0).getId() : 0;
        model.addAttribute("areas", areas);
        model.addAttribute("floors", floorService.getAllFloorsByAreaId(firstAreaId));
        model.addAttribute("rooms", roomService.getRoomsByAreaId(firstAreaId));
        model.addAttribute("registers", registerRoomService.getAllByAreaId(4));
        return "admin/register/index";
    }
}
