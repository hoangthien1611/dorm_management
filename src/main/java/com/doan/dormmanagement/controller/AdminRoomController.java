package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.model.Room;
import com.doan.dormmanagement.service.*;
import com.doan.dormmanagement.utility.TimeString;
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

    @Autowired
    private FloorService floorService;

    @Autowired
    private RoomFunctionService roomFunctionService;

    @Autowired
    private CostService costService;

    @GetMapping
    public String index(Model model) {
        int[] time = TimeString.getPreviousMonth();
        model.addAttribute("areas", areaService.getAllAreas());
        model.addAttribute("floors", floorService.getAllFloorsByAreaId(1));
        model.addAttribute("rooms", roomService.getRoomsByAreaId(1));
        model.addAttribute("yearMonth", TimeString.convertYearMonthtoString(time[1], time[0]));
        model.addAttribute("listFunction", roomFunctionService.getAllRoomFunction());
        model.addAttribute("costs", costService.getAllByType(1));
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

    @GetMapping("/floor/{id}")
    @ResponseBody
    public List<Room> showRoomsByFloor(@PathVariable("id") Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer floorId = Integer.parseInt(id.get());
                List<Room> result = roomService.getRoomsByFloorId(floorId);
                return result == null ? new ArrayList<>() : result;
            }

            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("areas", areaService.getAllAreas());
        model.addAttribute("floors", floorService.getAllFloorsByAreaId(1));
        model.addAttribute("listFunction", roomFunctionService.getAllRoomFunction());
        model.addAttribute("costs", costService.getAllByType(1));
        return "admin/room/add";
    }

    @PostMapping("/add")
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

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute Room room, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            ra.addFlashAttribute("msg", new Message(0, "Vui lòng nhập thông tin phù hợp!"));
        } else if (roomService.editRoom(room)) {
            ra.addFlashAttribute("msg", new Message(1, "Cập nhật phòng thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(0, "Cập nhật phòng thất bại!"));
        }
        return "redirect:/admin/room";
    }

    @PostMapping("/change-status")
    @ResponseBody
    public String changeStatus(@RequestParam("roomId") Integer id, @RequestParam("stt") Integer stt) {
        if (roomService.changeStatus(id, stt)) {
            return  "{\"msg\": \"OK\"}";
        }
        return null;
    }
}
