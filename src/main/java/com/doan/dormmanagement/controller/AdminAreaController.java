package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.model.Area;
import com.doan.dormmanagement.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            ra.addFlashAttribute("msg", new Message(0, "Vui lòng nhập thông tin phù hợp!"));
        } else if (areaService.editArea(area)) {
            ra.addFlashAttribute("msg", new Message(1, "Cập nhật thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(0, "Cập nhật thất bại!"));
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
            ra.addFlashAttribute("msg", new Message(0, "Vui lòng nhập thông tin phù hợp!"));
        } else if (areaService.addArea(area)) {
            ra.addFlashAttribute("msg", new Message(1, "Thêm thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(0, "Thêm thất bại!"));
        }

        return "redirect:/admin/area";
    }
}
