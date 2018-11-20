package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.model.RegisterRoom;
import com.doan.dormmanagement.model.Room;
import com.doan.dormmanagement.model.Area;
import com.doan.dormmanagement.service.RegisterRoomService;
import com.doan.dormmanagement.service.RoomService;
import com.doan.dormmanagement.service.FloorService;
import com.doan.dormmanagement.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dorm")
public class PublicRegisterRoomController {
    private final RegisterRoomService registerRoomService;

    private final AreaService areaService;

    private final RoomService roomService;

    private final FloorService floorService;

    @Autowired
    public PublicRegisterRoomController(RegisterRoomService registerRoomService, AreaService areaService, RoomService roomService, FloorService floorService) {
        this.registerRoomService = registerRoomService;
        this.areaService = areaService;
        this.roomService = roomService;
        this.floorService = floorService;
    }

    @GetMapping("/list-room")
    public String add(Model model) {
        List<Area> areas = areaService.getAllAreas();
//        int firstAreaId = areas.size() > 0 ? areas.get(0).getId() : 0;
        model.addAttribute("areas", areas);
        return "public/user/register_room";
    }

    @GetMapping("/register-room/{id}")
    public String add(Model model, @PathVariable("id") Integer id) {
        List<Area> areas = areaService.getAllAreas();
//        int firstAreaId = areas.size() > 0 ? areas.get(0).getId() : 0;
        model.addAttribute("roomsInArea", roomService.getRoomsByAreaId(id));
        model.addAttribute("floorsInArea", floorService.getAllFloorInAreas(id));
        model.addAttribute("areas", areas);
//        model.addAttribute("rooms", roomService.getRoomsByAreaId(firstAreaId));
        return "public/user/register_room";
    }

    @ResponseBody
    public List<Room> showRoomsByArea(@PathVariable("id") Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer areaId = Integer.parseInt(id.get());
                List<Room> result = roomService.getRoomsByAreaId(areaId);
                return result == null ? new ArrayList<>() : result;
            }

            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }

    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute RegisterRoom registerRoom, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            ra.addFlashAttribute("msg", new Message(0, "Vui lòng nhập thông tin phù hợp!"));
        } else if (registerRoomService.addRegisterRoom(registerRoom)) {
            ra.addFlashAttribute("msg", new Message(1, "Thêm khu nhà thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(0, "Thêm khu nhà thất bại!"));
        }

        return "redirect:/dorm";
    }
}
