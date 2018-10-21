package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.common.Constant;
import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.model.*;
import com.doan.dormmanagement.service.AreaService;
import com.doan.dormmanagement.service.FloorService;
import com.doan.dormmanagement.service.RegisterRoomService;
import com.doan.dormmanagement.service.RoomService;
import com.doan.dormmanagement.service.TimeRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import com.doan.dormmanagement.utility.TimeString;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

@Controller
@RequestMapping("/dorm/register-room")
public class PublicRegisterRoomController {

    @Autowired
    private RegisterRoomService registerRoomService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private FloorService floorService;

    @Autowired
    private TimeRegisterService timeRegisterService;

    @GetMapping("/list-room")
    public String index(Model model) {
        List<Area> areas = areaService.getAllAreas();
        int[] time = TimeString.getCurrentMonth();
        String currentTimeStamp = TimeString.getCurrentTimeStamp();
        int firstAreaId = areas.size() > 0 ? areas.get(0).getId() : 0;

        model.addAttribute("areas", areas);
        model.addAttribute("floors", floorService.getAllFloorInAreas(firstAreaId));
        model.addAttribute("rooms", roomService.getRoomsByAreaId(firstAreaId));
        model.addAttribute("timeRegister", timeRegisterService.getAllTimeRegisterByDate(1));
        model.addAttribute("registers", registerRoomService.getAllByAreaId(firstAreaId));
        model.addAttribute("timeCurrent",time);
        model.addAttribute("currentTimeStamp",currentTimeStamp);

        return "public/user/register_room";
//        return "public/user/register_room :: list_room";
    }

//    @GetMapping("/check-time-register")
//    @ResponseBody
//    public List<TimeRegister> getAllTimeRegisterByDate(@PathVariable("areaId") Optional<String> id) {
//        try {
//            if (id.isPresent()) {
//                Integer areaId = Integer.parseInt(id.get());
//                List<TimeRegister> list = timeRegisterService.getAllTimeRegisterByDate(areaId);
//                return list != null ? list : new ArrayList<>();
//            }
//
//            return new ArrayList<>();
//        } catch (NumberFormatException e) {
//            return new ArrayList<>();
//        }
//    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute RegisterRoom registerRoom, BindingResult br, RedirectAttributes ra) {

        if (br.hasErrors()) {
            ra.addFlashAttribute("msg", new Message(0, "Vui lòng chọn thông tin phù hợp!"));
        } else if (registerRoomService.addRegisterRoom(registerRoom)) {
            ra.addFlashAttribute("msg", new Message(1, "Đăng ký phòng thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(1, "Đăng ký phòng thành công!"));
        }

        return "redirect:/dorm/register-room/ajax-info-reg-room/3";
    }

    @GetMapping("/info-reg-room/{userId}")
    public String getInfoRegRoom(Model model, @PathVariable("userId") Integer userId) {
        model.addAttribute("registers", registerRoomService.getRegRoomByUserId(userId));
        return "public/user/info_reg_room";
    }

    @GetMapping("/ajax-info-reg-room/{userId}")
    public String ajaxGetInfoRegRoom(Model model, @PathVariable("userId") Integer userId) {
        model.addAttribute("registers", registerRoomService.getRegRoomByUserId(userId));
        return "public/user/info_reg_room :: info-reg-room";
    }

    @GetMapping("/delete/{regId}")
    public String delRegRoom(@PathVariable("regId") Integer regId, RedirectAttributes ra) {
        if (registerRoomService.delRegRoom(regId)) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Hủy đăng ký thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Hủy đăng ký thất bại!"));
        }

        return "redirect:/dorm/register-room/info-reg-room/3";
    }

    @GetMapping("/get-floor/{areaId}")
    @ResponseBody
    public List<Floor> getFloorsByAreaId(@PathVariable("areaId") Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer areaId = Integer.parseInt(id.get());
                List<Floor> list = floorService.getAllFloorInAreas(areaId);
                return list != null ? list : new ArrayList<>();
            }

            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @GetMapping("/get-room-in-area/{id}")
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

    @GetMapping("/get-room-in-floor/{id}")
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
}
