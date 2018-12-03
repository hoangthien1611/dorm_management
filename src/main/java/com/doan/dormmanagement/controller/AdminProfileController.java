package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.common.Constant;
import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.dto.PasswordChange;
import com.doan.dormmanagement.dto.UserUpdate;
import com.doan.dormmanagement.model.User;
import com.doan.dormmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/{username}")
    public String index(Model model, @PathVariable("username") Optional<String> username) {
        if (username.isPresent()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            if (!name.equals(username.get())) {
                return "admin/error/page_403";
            }
            User user = userService.getUserByUsername(username.get());
            if (user == null) {
                return "admin/error/page_404";
            }
            model.addAttribute("user", user);
            return "admin/profile/index";
        }
        return "admin/error/page_404";
    }

    @PostMapping("/{username}/change-password")
    public String changePassword(@ModelAttribute PasswordChange passwordChange, @PathVariable("username") String username, RedirectAttributes ra) {
        if (userService.changePassword(passwordChange)) {
            ra.addFlashAttribute("msg2", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Cập nhật thành công!"));
        } else {
            ra.addFlashAttribute("msg2", new Message(Constant.MESSAGE_TYPE_FAILURE, "Cập nhật thất bại!"));
        }

        return "redirect:/admin/profile/" + username;
    }

    @PostMapping("{username}/update-infor")
    public String updateInfor(@ModelAttribute UserUpdate userUpdate, @PathVariable("username") String username, RedirectAttributes ra) {
        if (userService.updateInfor(userUpdate)) {
            ra.addFlashAttribute("msg1", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Cập nhật thành công!"));
        } else {
            ra.addFlashAttribute("msg1", new Message(Constant.MESSAGE_TYPE_FAILURE, "Cập nhật thất bại!"));
        }
        return "redirect:/admin/profile/" + username;
    }
}
