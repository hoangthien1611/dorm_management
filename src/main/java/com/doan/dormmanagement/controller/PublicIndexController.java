package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dorm")
public class PublicIndexController {

    @GetMapping
    public String index() {
        return "public/index";
    }
}
