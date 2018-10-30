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
@RequestMapping("/admin/subsistence_fee")
public class AdminSubsistenceFeeController {

    @Autowired
    private SubsistenceFeeService subsistenceFeeService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("feeList", subsistenceFeeService.getAllSubsistenceFee());
        return "admin/fee/index";
    }

    @GetMapping("/add")
    public String add() {
        return "admin/fee/add";
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
}
