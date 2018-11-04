package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.model.Area;
import com.doan.dormmanagement.model.SubsistenceFee;
import com.doan.dormmanagement.service.AreaService;
import com.doan.dormmanagement.service.SubsistenceFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/subsistence")
public class AdminSubsistenceFeeController {

    @Autowired
    private SubsistenceFeeService subsistenceFeeService;

    @Autowired
    private AreaService areaService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("feeList", subsistenceFeeService.getAllByMonthAndYear(9,2018));
        return "admin/subsistence/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("areas", areaService.getAllAreas());
        return "admin/subsistence/add";
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

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute SubsistenceFee subsistenceFee, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            ra.addFlashAttribute("msg", new Message(0, "Vui lòng nhập thông tin phù hợp!"));
        } else if (subsistenceFeeService.editSubsistenceFee(subsistenceFee)) {
            ra.addFlashAttribute("msg", new Message(1, "Cập nhật thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(0, "Cập nhật thất bại!"));
        }
        return "redirect:/admin/subsistence";
    }
}
