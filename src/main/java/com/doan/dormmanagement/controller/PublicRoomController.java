package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.service.AreaService;
import com.doan.dormmanagement.service.FloorService;
import com.doan.dormmanagement.service.RoomService;
import com.doan.dormmanagement.service.UserService;
import com.doan.dormmanagement.service.SubsistenceFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.doan.dormmanagement.utility.TimeString;

import java.time.LocalDate;
import java.util.Calendar;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dorm/room")
public class PublicRoomController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private FloorService floorService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubsistenceFeeService subsistenceFeeService;

    @GetMapping("/area/{id}")
    public String index(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("roomAreas", roomService.getRoomsByAreaId(id));
        model.addAttribute("areas", areaService.getAllAreas());
        return "public/room/list_room :: listRoom";
    }

    @GetMapping("/detail/{id}")
    public String getDetailRoom(Model model, @PathVariable("id") Integer id) {
        int[] time = TimeString.getPreviousMonth();

        model.addAttribute("roomDes", roomService.getRoomsByRoomId(id));
        model.addAttribute("usersInRoom", userService.getUsersbyRoomId(id));
        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("subsistence", subsistenceFeeService.getSubsistenceFeeByRoomIdWithDate(id, time[0], time[1]));
        return "public/room/list_room :: detailRoom";
    }

    @GetMapping("/getSubsistence/idRoom={roomId}/month={month}/year={year}")
    public String getSubsistenceRoom(Model model, @PathVariable("roomId") Integer roomId, @PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        model.addAttribute("subsistence", subsistenceFeeService.getSubsistenceFeeByRoomIdWithDate(roomId, month, year));
        return "public/room/list_room :: subsistenceOfRoom";
    }

    @GetMapping("/floor/{id}")
    public String getFloorsByAreaId(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("floorInAreas", floorService.getAllFloorInAreas(id));
        model.addAttribute("areas", areaService.getAllAreas());
        return "public/room/list_room";
    }

    @GetMapping("/area/{areaId}/floor/{floorId}")
    public String getRoomsByFloorId(Model model, @PathVariable("areaId") Integer areaId, @PathVariable("floorId") Integer floorId) {
        model.addAttribute("areas", areaService.getAllAreas());
        model.addAttribute("roomAreas", roomService.getRoomsByAreaId(areaId));
        model.addAttribute("roomFloors", roomService.getAllRoomsInFloor(areaId, floorId));
        model.addAttribute("roomFloors", roomService.getAllRoomsInFloor(areaId, floorId));
        return "public/room/list_room";
    }
}
