package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.common.Constant;
import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.dto.PasswordChange;
import com.doan.dormmanagement.dto.UserUpdate;
import com.doan.dormmanagement.model.User;
import com.doan.dormmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/profile")
public class AdminProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public String index(Model model, @PathVariable("userId") Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer userId = Integer.parseInt(id.get());
                User user = userService.getUserByUserId(userId);
                if (user == null) {
                    return "admin/error/page_404";
                }
                model.addAttribute("user", user);
                return "admin/profile/index";
            }
            return "admin/error/page_404";
        } catch (NumberFormatException e) {
            return "admin/error/page_500";
        }
    }

    @PostMapping("/{userId}/change-password")
    public String changePassword(@ModelAttribute PasswordChange passwordChange, @PathVariable("userId") Integer userId, RedirectAttributes ra) {
        if (userService.changePassword(passwordChange)) {
            ra.addFlashAttribute("msg2", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Cập nhật thành công!"));
        } else {
            ra.addFlashAttribute("msg2", new Message(Constant.MESSAGE_TYPE_FAILURE, "Cập nhật thất bại!"));
        }

        return "redirect:/admin/profile/" + userId;
    }

    @PostMapping("{userId}/update-infor")
    public String updateInfor(@ModelAttribute UserUpdate userUpdate, @PathVariable("userId") Integer userId, RedirectAttributes ra) {
        if (userService.updateInfor(userId, userUpdate)) {
            ra.addFlashAttribute("msg1", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Cập nhật thành công!"));
        } else {
            ra.addFlashAttribute("msg1", new Message(Constant.MESSAGE_TYPE_FAILURE, "Cập nhật thất bại!"));
        }
        return "redirect:/admin/profile/" + userId;
    }
}
