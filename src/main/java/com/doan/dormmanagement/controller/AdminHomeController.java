package com.doan.dormmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {

    @GetMapping("/admin/index")
    public String index() {
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
