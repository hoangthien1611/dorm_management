package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.common.Constant;
import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.dto.UserLogin;
import com.doan.dormmanagement.service.UserService;
import com.doan.dormmanagement.utility.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AdminAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionInfo sessionInfo;

    @GetMapping("/admin/login")
    public String login(HttpServletRequest request, @RequestParam(required = false) String message, Model model) {
//        if (sessionInfo.getCurrentUser(request) != null) {
//            return "redirect:/admin/area";
//        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!"anonymousUser".equals(principal)) {
            return "redirect:/admin/index";
        }

        if (message != null && !message.isEmpty()) {
            if (message.equals("logout")) {
                model.addAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Bạn đã logout thành công!"));
            }
            if (message.equals("error")) {
                model.addAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Sai username hoặc mật khẩu!"));
            }
            if (message.equals("timeout")) {
                model.addAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Time out! Vui lòng đăng nhập lại!"));
            }
            if (message.equals("max_session")) {
                model.addAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Tài khoản này đã được đăng nhập trên thiết bị khác!"));
            }
        }

        return "admin/auth/login";
    }

//    @PostMapping("/admin/login")
//    public String login(@ModelAttribute UserLogin userLogin, RedirectAttributes ra, HttpServletRequest request) {
//        if ("".equals(userLogin.getUserName()) || "".equals(userLogin.getPassword())) {
//            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Vui lòng nhập vào username và password!"));
//        } else if (userService.login(userLogin)) {
//            HttpSession session = request.getSession();
//            session.setAttribute(Constant.USER_INFO, userLogin.getUserName());
//            return "redirect:/admin/area";
//        } else {
//            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Sai username hoặc password!"));
//        }
//        return "redirect:/admin/login";
//    }

    @GetMapping("/admin/logout")
    public String logout(HttpServletRequest request, RedirectAttributes ra) {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constant.USER_INFO) != null) {
            session.removeAttribute(Constant.USER_INFO);
        }
        ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Bạn đã logout thành công!"));
        return "redirect:/admin/login";
    }
}
