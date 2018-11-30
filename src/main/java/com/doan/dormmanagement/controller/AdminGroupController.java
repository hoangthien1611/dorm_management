package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.model.Group;
import com.doan.dormmanagement.service.ActionService;
import com.doan.dormmanagement.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/group")
public class AdminGroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private ActionService actionService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("groups", groupService.getAllGroup());
        return "admin/group/index";
    }

    @GetMapping("/detail")
    public String detailGroup(Model model, @RequestParam("group") Optional<String> gId) {
        try {
            List<Group> groups = groupService.getAllGroup();

            if (gId.isPresent()) {
                Integer groupId = Integer.parseInt(gId.get());
                model.addAttribute("actions", groupService.getAllActionsByGroupId(groupId));
                model.addAttribute("groupDefault", groupId);
            } else {
                Integer firstGroupId = groups.size() > 0? groups.get(0).getId() : 0;
                model.addAttribute("actions", groupService.getAllActionsByGroupId(firstGroupId));
            }

            model.addAttribute("groups", groups);
            model.addAttribute("actionsAdd", actionService.getAllActions());
            return "admin/group/detail";
        } catch (NumberFormatException e) {
            return "admin/error/page_404";
        }
    }
}
