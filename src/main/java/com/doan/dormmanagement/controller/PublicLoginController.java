package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.model.User;
import com.doan.dormmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/dorm")
public class PublicLoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "public/login";
    }

    @PostMapping("/login")
    public String checkLogin(@Valid @ModelAttribute User user, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            ra.addFlashAttribute("msg", new Message(1, "Vui lòng nhập thông tin phù hợp!"));
            return "redirect:/dorm/login";
        }
        if (!userService.getLogin(user)) {
            ra.addFlashAttribute("msg", new Message(1, "Sai username hoặc password!"));
            return "redirect:/dorm/login";
        }
//        HttpSession httpSession = request.getSession();
//        httpSession.setAttribute("userName", 1);

//        HttpSession session = request.getSession(); //Creating a session
//        session.setAttribute("userName", userName); //setting session attribute
//        request.setAttribute("userName", userName);
        return "redirect:/dorm";
    }
}
