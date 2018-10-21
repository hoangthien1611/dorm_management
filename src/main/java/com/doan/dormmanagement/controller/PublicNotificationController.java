package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.model.Notification;
import com.doan.dormmanagement.service.NotificationService;
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
@RequestMapping("/dorm/notification")
public class PublicNotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user/{id}")
    public String index(Model model, @PathVariable("id") Integer userId) {
        model.addAttribute("nots", notificationService.getNotificationByUserId(userId));
        return "public/user/notification";
    }

    @GetMapping("/detail/{id}")
    public String getNotifyDetail(Model model, @PathVariable("id") Integer notifyId) {
        model.addAttribute("notDes", notificationService.getNotificationByNotifyId(notifyId));
        return "public/user/notification :: notifyDetail";
    }
}
