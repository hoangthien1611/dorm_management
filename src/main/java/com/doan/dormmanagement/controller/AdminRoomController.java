package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.model.Room;
import com.doan.dormmanagement.service.AreaService;
import com.doan.dormmanagement.service.RoomService;
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
@RequestMapping("/admin/room")
public class AdminRoomController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private RoomService roomService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("rooms", roomService.getRoomsByAreaId(1));
        model.addAttribute("areas", areaService.getAllAreas());
        return "admin/room/index";
    }

    @GetMapping("/area/{id}")
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

    @GetMapping("/add")
    public String add() {
        return "admin/room/add";
    }

    public String add(@Valid @ModelAttribute Room room, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            ra.addFlashAttribute("msg", new Message(0, "Vui lòng nhập thông tin phù hợp!"));
        } else if (roomService.addRoom(room)) {
            ra.addFlashAttribute("msg", new Message(1, "Thêm phòng thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(0, "Thêm phòng thất bại!"));
        }

        return "redirect:/admin/room";
    }
}
