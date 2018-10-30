package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/student")
public class AdminStudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String index() {
        return "admin/student/index";
    }

    @GetMapping("/add")
    public String add() {
        return "admin/student/add";
    }
}
