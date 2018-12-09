package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.common.Constant;
import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.model.Area;
import com.doan.dormmanagement.model.SubsistenceFee;
import com.doan.dormmanagement.service.AreaService;
import com.doan.dormmanagement.service.FloorService;
import com.doan.dormmanagement.service.RoomService;
import com.doan.dormmanagement.service.SubsistenceFeeService;
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
@RequestMapping("/admin/subsistence")
public class AdminSubsistenceFeeController {

    @Autowired
    private SubsistenceFeeService subsistenceFeeService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private FloorService floorService;

    @ModelAttribute
    public void addCommonObjects(Model model) {
        model.addAttribute("title", "Quản lý phí điện nước");
    }

    @GetMapping
    public String index(Model model, @RequestParam("area") Optional<String> aId, @RequestParam("floor") Optional<String> fId,
                        @RequestParam("month") Optional<String> m, @RequestParam("year") Optional<String> y) {
        try {
            List<Area> areas = areaService.getAllAreas();

            if (aId.isPresent() && fId.isPresent() && m.isPresent() && y.isPresent()) {
                Integer areaId = Integer.parseInt(aId.get());
                Integer floorId = Integer.parseInt(fId.get());
                Integer month = Integer.parseInt(m.get());
                Integer year = Integer.parseInt(y.get());

                if (floorId == 0) {
                    model.addAttribute("feeList", subsistenceFeeService.getAllByMonthAndYearAndArea(month, year, areaId));
                } else {
                    model.addAttribute("feeList", subsistenceFeeService.getAllByMonthAndYearAndFloor(month, year, floorId));
                }
                model.addAttribute("floors", floorService.getAllFloorsByAreaId(areaId));
                model.addAttribute("yearMonth", TimeString.convertYearMonthtoString(year, month));
                model.addAttribute("areaDefault", areaId);
                model.addAttribute("floorDefault", floorId);
            } else {
                int firstAreaId = areas.size() > 0 ? areas.get(0).getId() : 0;
                int[] time = TimeString.getPreviousMonth();
                model.addAttribute("floors", floorService.getAllFloorsByAreaId(firstAreaId));
                model.addAttribute("feeList", subsistenceFeeService.getAllByMonthAndYearAndArea(time[0], time[1], firstAreaId));
                model.addAttribute("yearMonth", TimeString.convertYearMonthtoString(time[1], time[0]));
            }

            model.addAttribute("areas", areas);
            return "admin/subsistence/index";
        } catch (NumberFormatException e) {
            return "admin/error/page_404";
        }
    }

    @ResponseBody
    @GetMapping("/area/{areaId}/{month}/{year}")
    public List<SubsistenceFee> getAllSubsistenceByAreaAndTime(@PathVariable("areaId") Optional<String> id, @PathVariable("month") Optional<String> m, @PathVariable("year") Optional<String> y) {
        try {
            if (id.isPresent() && m.isPresent() && y.isPresent()) {
                Integer areaId = Integer.parseInt(id.get());
                Integer month = Integer.parseInt(m.get());
                Integer year = Integer.parseInt(y.get());

                List<SubsistenceFee> list = subsistenceFeeService.getAllByMonthAndYearAndArea(month, year, areaId);
                return list != null ? list : new ArrayList<>();
            }
            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @ResponseBody
    @GetMapping("/floor/{floorId}/{month}/{year}")
    public List<SubsistenceFee> getAllSubsistenceByFloorAndTime(@PathVariable("floorId") Optional<String> id, @PathVariable("month") Optional<String> m, @PathVariable("year") Optional<String> y) {
        try {
            if (id.isPresent() && m.isPresent() && y.isPresent()) {
                Integer floorId = Integer.parseInt(id.get());
                Integer month = Integer.parseInt(m.get());
                Integer year = Integer.parseInt(y.get());

                List<SubsistenceFee> list = subsistenceFeeService.getAllByMonthAndYearAndFloor(month, year, floorId);
                return list != null ? list : new ArrayList<>();
            }
            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<Area> areas = areaService.getAllAreas();
        int firstAreaId = areas.size() > 0 ? areas.get(0).getId() : 0;
        model.addAttribute("areas", areas);
        model.addAttribute("rooms", roomService.getRoomsByAreaId(firstAreaId));
        return "admin/subsistence/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute SubsistenceFee subsistenceFee, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Vui lòng nhập đầy đủ thông tin phù hợp!"));
            return "redirect:/admin/subsistence/add";
        }
        if (subsistenceFeeService.addSubsistenceFee(subsistenceFee)) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Thêm thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Thêm thất bại!"));
        }

        return "redirect:/admin/subsistence";
    }

    @GetMapping("/room/{id}")
    @ResponseBody
    public List<SubsistenceFee> getSubsistenceFeeByRoomId(@PathVariable Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer roomId = Integer.parseInt(id.get());
                return subsistenceFeeService.getSubsistenceFeeByRoomId(roomId);
            }

            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @GetMapping("/room/{id}/{month}/{year}")
    @ResponseBody
    public List<SubsistenceFee> getSubsistenceFeeByRoomIdAndTime(@PathVariable("id") Optional<String> id, @PathVariable("month") Optional<String> m, @PathVariable("year") Optional<String> y) {
        try {
            if (id.isPresent() && m.isPresent() && y.isPresent()) {
                Integer roomId = Integer.parseInt(id.get());
                Integer month = Integer.parseInt(m.get());
                Integer year = Integer.parseInt(y.get());
                List<SubsistenceFee> list = subsistenceFeeService.getSubsistenceFeeByRoomIdAndTime(roomId, month, year);
                return list != null ? list : new ArrayList<>();
            }
            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute SubsistenceFee subsistenceFee, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors() || subsistenceFee.getNewNumberWater() < subsistenceFee.getOldNumberWater() || subsistenceFee.getNewNumberElec() < subsistenceFee.getOldNumberElec()) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Vui lòng nhập thông tin phù hợp!"));
        } else if (subsistenceFeeService.editSubsistenceFee(subsistenceFee)) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Cập nhật thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Cập nhật thất bại!"));
        }
        return "redirect:/admin/subsistence?area=" + subsistenceFee.getAreaId() + "&floor=" + subsistenceFee.getFloorId()
                + "&month=" + subsistenceFee.getMonth() + "&year=" + subsistenceFee.getYear();
    }

    @PostMapping("/paid")
    @ResponseBody
    public String changeStatusToPaid(@RequestParam("feeId") Integer id) {
        if (subsistenceFeeService.changeStatusToPaid(id)) {
            return "OK";
        }
        return null;
    }
}
