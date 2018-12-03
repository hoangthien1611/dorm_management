package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.common.Constant;
import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.model.Action;
import com.doan.dormmanagement.model.Group;
import com.doan.dormmanagement.model.Role;
import com.doan.dormmanagement.service.ActionService;
import com.doan.dormmanagement.service.GroupService;
import com.doan.dormmanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/group")
public class AdminGroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private ActionService actionService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("groups", groupService.getAllGroup());
        return "admin/group/index";
    }

    @GetMapping("/detail")
    public String detailGroup(Model model, @RequestParam("group") Optional<String> gId) {
        try {
            List<Group> groups = groupService.getAllGroup();
            List<Action> actions = new ArrayList<>();

            if (gId.isPresent()) {
                Integer groupId = Integer.parseInt(gId.get());
                actions = actionService.getAllActionsByGroupId(groupId);
                model.addAttribute("groupDefault", groupId);
            } else {
                Integer firstGroupId = groups.size() > 0? groups.get(0).getId() : 0;
                actions = actionService.getAllActionsByGroupId(firstGroupId);
                model.addAttribute("groupDefault", firstGroupId);
            }

            List<Action> allActions = actionService.getAllActions();

            model.addAttribute("groups", groups);
            model.addAttribute("actions", actions);
            model.addAttribute("actionsAdd", getActionsAdd(allActions, actions));
            return "admin/group/detail";
        } catch (NumberFormatException e) {
            return "admin/error/page_404";
        }
    }

    @PostMapping("/add")
    public String addGroup(@ModelAttribute Group group, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Vui lòng nhập vào tên group!"));
        } else if (groupService.addGroup(group)) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Thêm group thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Thêm group thất bại!"));
        }
        return "redirect:/admin/group";
    }

    @PostMapping("/edit")
    public String editGroup(@ModelAttribute Group group, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Vui lòng nhập vào tên group!"));
        } else if (groupService.editGroup(group)) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Cập nhật group thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Cập nhật group thất bại!"));
        }
        return "redirect:/admin/group";
    }

    @GetMapping("/delete")
    public String delGroup(@RequestParam("gId") String gId, RedirectAttributes ra) {
        try {
            Integer groupId = Integer.parseInt(gId);
            if (groupService.delGroup(groupId)) {
                ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Xóa group thành công!"));
            } else {
                ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Xóa group thất bại!"));
            }
            return "redirect:/admin/group";
        } catch (NumberFormatException e) {
            return "admin/error/page_500";
        }
    }

    @PostMapping("/detail/add-action")
    public String addAction(@ModelAttribute Role role, RedirectAttributes ra) {
        if (role.getActionId() == 0) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Không có action nào để thêm!"));
        } else if (roleService.addActionToGroup(role.getGroupId(), role.getActionId())) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Thêm action thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Thêm action thất bại!"));
        }
        return "redirect:/admin/group/detail?group=" + role.getGroupId();
    }

    @GetMapping("/detail/del-action")
    public String addAction(@RequestParam("aId") String aId, @RequestParam("gId") String gId, RedirectAttributes ra) {
        try {
            Integer actionId = Integer.parseInt(aId);
            Integer groupId = Integer.parseInt(gId);
            if (roleService.delRole(groupId, actionId)) {
                ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Xóa action thành công!"));
            } else {
                ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Xóa action thất bại!"));
            }
            return "redirect:/admin/group/detail?group=" + groupId;
        } catch (NumberFormatException e) {
            return "admin/error/page_500";
        }
    }

    @GetMapping("/detail/get-actions-add/{gId}")
    @ResponseBody
    public List<Action> getActionsAdd(@PathVariable("gId") String gId) {
        try {
            Integer groupId = Integer.parseInt(gId);
            List<Action> allActions = actionService.getAllActions();
            List<Action> actions = actionService.getAllActionsByGroupId(groupId);
            return getActionsAdd(allActions, actions);
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    private List<Action> getActionsAdd(List<Action> allActions, List<Action> actions) {
        if (actions != null && actions.size() > 0) {
            return allActions.stream()
                    .filter(act -> !isActionBelongsToList(actions, act))
                    .collect(Collectors.toList());
        }
        return allActions;
    }

    private boolean isActionBelongsToList(List<Action> list, Action action) {
        return list.stream()
                .anyMatch(act -> act.getId() == action.getId());
    }
}
