package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.common.Constant;
import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.model.User;
import com.doan.dormmanagement.service.UserService;
import com.doan.dormmanagement.utility.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.doan.dormmanagement.model.UserRegister;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class PublicAccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionInfo sessionInfo;

    @GetMapping("/dorm/login")
    public String login(HttpServletRequest request, @RequestParam(required = false) String message, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!"anonymousUser".equals(principal)) {
            String username = ((UserDetails)principal).getUsername();
            User user = userService.getUserByUsername(username);
//            Integer userId = user.getId();
            HttpSession session = request.getSession();
            session.setAttribute("authUserId",user.getId());
            return "redirect:/dorm";
        }

        if (message != null && !message.isEmpty()) {
            if (message.equals("logout")) {
                model.addAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Bạn đã logout thành công!"));
            }
            if (message.equals("error")) {
                model.addAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Sai mã sinh viên hoặc mật khẩu!"));
            }
            if (message.equals("timeout")) {
                model.addAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Time out! Vui lòng đăng nhập lại!"));
            }
            if (message.equals("max_session")) {
                model.addAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Tài khoản này đã được đăng nhập trên thiết bị khác!"));
            }
        }

        return "public/user/login";
    }

    @GetMapping("/dorm/logout")
    public String logout(HttpServletRequest request, RedirectAttributes ra) {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constant.USER_INFO) != null) {
            session.removeAttribute(Constant.USER_INFO);
        }
        ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Bạn đã logout thành công!"));
        return "redirect:/dorm/login";
    }

    @GetMapping("/dorm/profile/{username}")
    public String index(Model model, @PathVariable("username") Optional<String> username) {
        if (username.isPresent()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            if (!name.equals(username.get())) {
                return "public/error/page_403";
            }
            User user = userService.getUserByUsername(username.get());
            if (user == null) {
                return "public/error/page_404";
            }
            model.addAttribute("user", user);
            return "layout/public";
        }
        return "public/error/page_404";
    }

    @GetMapping("/dorm/register-acc")
    public String signUp() {
        return "public/user/sign_up";
    }

    @PostMapping("/dorm/register-acc")
    public String add(@Valid @ModelAttribute UserRegister user, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Vui lòng nhập đầy đủ thông tin phù hợp!"));
            return "redirect:/dorm/user/add";
        }

        String regexUser = "^[a-zA-Z0-9_]*$";
        Pattern patternUser = Pattern.compile(regexUser);
        Matcher matcher = patternUser.matcher(user.getUserName());
        if (!matcher.matches()) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Username chỉ gồm chữ, số và kí tự _"));
            return "redirect:/dorm/user/add";
        }

        String regexPhone = "(\\+84|0)\\d{9}";
        Pattern patternPhone = Pattern.compile(regexPhone);
        Matcher matcherPhone = patternPhone.matcher(user.getPhone());
        if (!matcherPhone.matches()) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Số điện thoại bao gồm 10 chữ số bắt đầu là +84 hoặc 0"));
            return "redirect:/dorm/user/add";
        }

        String regexCode = "^(10)[1-9][0-9]{6}$";
        Pattern patternCode = Pattern.compile(regexCode);
        Matcher matcherCode = patternCode.matcher(user.getMssv());
        if (!matcherCode.matches()) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Mã số sinh viên gồm 9 số và bắt đầu là 10* với * nhận giá trị từ 1 đến 9"));
            return "redirect:/dorm/user/add";
        }

        if (userService.addStudent(user)) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Đăng ký tài khoản thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Đăng ký tài khoản thất bại!"));
        }
        return "redirect:/dorm";
    }
}
