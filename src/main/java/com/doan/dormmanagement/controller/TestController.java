package com.doan.dormmanagement.controller;


import com.doan.dormmanagement.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @Autowired
    TestService service;

    // Test list
    @GetMapping("/test")
    public String list(Model model) {
        model.addAttribute("list", service.getAllUsers());
        return "/public/list";
    }

    @GetMapping("/admin/index")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/admin/add")
    public String add() {
        return "admin/add";
    }

    @GetMapping("/admin/list")
    public String list() {
        return "admin/list";
    }

    @GetMapping("/public/index")
    public String indexPublic() {
        return "public/index";
    }

    @GetMapping("/public/staff")
    public String staff() {
        return "public/managerial_staff";
    }

    @GetMapping("/public/profile")
    public String profile() {
        return "public/profile_user";
    }
}
