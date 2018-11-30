package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.utility.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public class AdminBaseController {

    @Autowired
    private SessionInfo sessionInfo;

    @ModelAttribute
    public void sendCommonInfo(Model model) {
    }
}
