package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.common.Constant;
import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.model.Area;
import com.doan.dormmanagement.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/area")
public class AdminAreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("areas", areaService.getAllAreas());
        return "admin/area/index";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute Area area, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Vui lòng nhập thông tin phù hợp!"));
        } else if (areaService.editArea(area)) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Cập nhật " + area.getName() + " thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Cập nhật " + area.getName() + " thất bại!"));
        }

        return "redirect:/admin/area";
    }

    @GetMapping("/add")
    public String add() {
        return "admin/area/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute Area area, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Vui lòng nhập thông tin phù hợp!"));
        } else if (areaService.addArea(area)) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Thêm khu nhà thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Thêm khu nhà thất bại!"));
        }

        return "redirect:/admin/area";
    }

    @PostMapping("/change-status")
    @ResponseBody
    public String changeStatus(@RequestParam("areaId") Integer id, @RequestParam("stt") Integer stt) {
        if (areaService.changeStatus(id, stt)) {
            return "OK";
        }
        return null;
    }
}
