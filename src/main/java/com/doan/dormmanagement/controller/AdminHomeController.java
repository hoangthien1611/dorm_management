package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AdminHomeController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addCommonObjects(Model model) {
        model.addAttribute("title", "Trang chủ Admin");
    }

    @GetMapping("/admin/index")
    public String index(Model model) {
        model.addAttribute("info", userService.getInfoIndex());
        return "admin/index/index";
    }

    @GetMapping("/403")
    public String error403() {
        return "admin/error/page_403";
    }

    @GetMapping("/404")
    public String error404() {
        return "admin/error/page_404";
    }

    @GetMapping("/500")
    public String error500() {
        return "admin/error/page_500";
    }
}
