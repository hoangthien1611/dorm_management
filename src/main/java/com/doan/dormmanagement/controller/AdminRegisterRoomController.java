package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.common.Constant;
import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.model.Area;
import com.doan.dormmanagement.model.RegisterRoom;
import com.doan.dormmanagement.service.AreaService;
import com.doan.dormmanagement.service.FloorService;
import com.doan.dormmanagement.service.RegisterRoomService;
import com.doan.dormmanagement.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        model.addAttribute("floors", floorService.getAllFloorInAreas(firstAreaId));
        model.addAttribute("rooms", roomService.getRoomsByAreaId(firstAreaId));
        model.addAttribute("registers", registerRoomService.getAllByAreaId(firstAreaId));
        return "admin/register/index";
    }

    @GetMapping("/area/{id}")
    @ResponseBody
    public List<RegisterRoom> getAllByAreaId(@PathVariable("id") Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer areaId = Integer.parseInt(id.get());
                List<RegisterRoom> list = registerRoomService.getAllByAreaId(areaId);
                return list != null ? list : new ArrayList<>();
            }

            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @GetMapping("/room/{id}")
    @ResponseBody
    public List<RegisterRoom> getAllByRoomId(@PathVariable("id") Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer roomId = Integer.parseInt(id.get());
                List<RegisterRoom> list = registerRoomService.getAllByRoomId(roomId);
                return list != null ? list : new ArrayList<>();
            }

            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @GetMapping("/floor/{id}")
    @ResponseBody
    public List<RegisterRoom> getAllByFloorId(@PathVariable("id") Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer floorId = Integer.parseInt(id.get());
                List<RegisterRoom> list = registerRoomService.getAllByFloorId(floorId);
                return list != null ? list : new ArrayList<>();
            }

            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @PostMapping("/accept-all")
    public String acceptAll(@RequestParam("regId") String[] idArr, RedirectAttributes ra) {
        try {
            if (idArr != null && idArr.length > 0) {
                Integer[] intArr = new Integer[idArr.length];
                for (int i = 0; i < idArr.length; i++) {
                    intArr[i] = Integer.parseInt(idArr[i]);
                }
                if (registerRoomService.acceptAll(intArr)) {
                    ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Phê duyệt thành công!"));
                } else {
                    ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Phê duyệt thất bại!"));
                }
            }

        } catch (NumberFormatException e) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Có lỗi trong quá trình xử lý!"));
        }
        return "redirect:/admin/register-room";
    }
}
