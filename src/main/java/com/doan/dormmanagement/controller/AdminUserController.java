package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.common.Constant;
import com.doan.dormmanagement.dto.Message;
import com.doan.dormmanagement.dto.PasswordChange;
import com.doan.dormmanagement.dto.UserRegister;
import com.doan.dormmanagement.model.Area;
import com.doan.dormmanagement.model.Group;
import com.doan.dormmanagement.model.User;
import com.doan.dormmanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private FloorService floorService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ActionService actionService;

    @Autowired
    private RoleUserService roleUserService;

    @GetMapping("/student")
    public String indexUser(Model model, @RequestParam("area") Optional<String> aId,
                            @RequestParam("floor") Optional<String> fId, @RequestParam("room") Optional<String> rId) {
        try {
            List<Area> areas = areaService.getAllAreas();

            if (aId.isPresent() && fId.isPresent() && rId.isPresent()) {
                Integer areaId = Integer.parseInt(aId.get());
                Integer floorId = Integer.parseInt(fId.get());
                Integer roomId = Integer.parseInt(rId.get());

                if (floorId == 0) {
                    model.addAttribute("rooms", roomService.getRoomsByAreaId(areaId));
                } else {
                    model.addAttribute("rooms", roomService.getRoomsByFloorId(floorId));
                }

                if (roomId == 0) {
                    if (floorId == 0) {
                        model.addAttribute("users", userService.getAllUsersByAreaId(areaId));
                    } else {
                        model.addAttribute("users", userService.getAllUsersByFloorId(floorId));
                    }
                } else {
                    model.addAttribute("users", userService.getAllUsersByRoomId(roomId));
                }

                model.addAttribute("floors", floorService.getAllFloorsByAreaId(areaId));
                model.addAttribute("areaDefault", areaId);
                model.addAttribute("floorDefault", floorId);
                model.addAttribute("roomDefault", roomId);
            } else {
                int firstAreaId = areas.size() > 0 ? areas.get(0).getId() : 0;
                model.addAttribute("floors", floorService.getAllFloorsByAreaId(firstAreaId));
                model.addAttribute("rooms", roomService.getRoomsByAreaId(firstAreaId));
                model.addAttribute("users", userService.getAllUsers());
            }

            model.addAttribute("areas", areas);
            return "admin/user/student";
        } catch (NumberFormatException e) {
            return "admin/error/page_500";
        }
    }

    @GetMapping("/employee")
    public String employeeIndex(Model model, @RequestParam("group") Optional<String> gId) {
        try {
            List<Group> groups = getListGroupsExceptOneGroup("Sinh viên");
            List<User> users = new ArrayList<>();

            if (gId.isPresent()) {
                Integer groupId = Integer.parseInt(gId.get());
                users = userService.getAllUsersByGroupId(groupId);
                model.addAttribute("groupDefault", groupId);
            } else {
                for (Group group : groups) {
                    List<User> list = userService.getAllUsersByGroupId(group.getId());
                    if (list != null && list.size() > 0) {
                        users.addAll(userService.getAllUsersByGroupId(group.getId()));
                    }
                }
            }

            model.addAttribute("users", users);
            model.addAttribute("groups", groups);
            return "admin/user/employee";
        } catch (NumberFormatException e) {
            return "admin/error/page_500";
        }
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("groups", getListGroupsExceptOneGroup("Sinh viên"));
        return "admin/user/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute UserRegister user, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Vui lòng nhập đầy đủ thông tin phù hợp!"));
            return "redirect:/admin/user/add";
        }

        String regex = "^[a-zA-Z0-9_]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user.getUserName());
        if (!matcher.matches()) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Username chỉ gồm chữ, số và kí tự _"));
            return "redirect:/admin/user/add";
        }

        if (userService.addUser(user)) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Thêm user thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Thêm user thất bại!"));
        }
        return "redirect:/admin/user/employee";
    }

    @GetMapping("/student/room/{roomId}")
    @ResponseBody
    public List<User> getUsersByRoomId(@PathVariable("roomId") Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer roomId = Integer.parseInt(id.get());
                List<User> list = userService.getAllUsersByRoomId(roomId);
                return list != null ? list : new ArrayList<>();
            }
            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @GetMapping("/student/floor/{floorId}")
    @ResponseBody
    public List<User> getUsersByFloorId(@PathVariable("floorId") Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer floorId = Integer.parseInt(id.get());
                List<User> list = userService.getAllUsersByFloorId(floorId);
                return list != null ? list : new ArrayList<>();
            }
            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @GetMapping("/student/area/{areaId}")
    @ResponseBody
    public List<User> getUsersByAreaId(@PathVariable("areaId") Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer areaId = Integer.parseInt(id.get());
                List<User> list = userService.getAllUsersByAreaId(areaId);
                return list != null ? list : new ArrayList<>();
            }
            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @PostMapping("/change-status")
    @ResponseBody
    public String changeStatus(@RequestParam("userId") Integer id, @RequestParam("stt") Integer stt) {
        if (userService.changeStatus(id, stt)) {
            return "OK";
        }
        return null;
    }

    @PostMapping("/change-status-action")
    @ResponseBody
    public String changeStatus(@RequestParam("username") String username, @RequestParam("actionId") Integer actionId) {
        if (roleUserService.changeStatusActionByUsernameAndActionId(username, actionId)) {
            return "OK";
        }
        return null;
    }

    @PostMapping("/reset-password")
    @ResponseBody
    public String resetPassword(@ModelAttribute PasswordChange passwordChange) {
        if (userService.resetPassword(passwordChange)) {
            return "OK";
        }
        return null;
    }

    @PostMapping("/check-username")
    @ResponseBody
    public String checkUserNameExisted(@RequestParam("username") String username) {
        if (userService.isUserNameExisted(username)) {
            return "Existed";
        }
        return null;
    }

    @PostMapping("/change-group")
    public String changeGroup(@RequestParam("userId") Integer userId, @RequestParam("groupId") Integer groupId, RedirectAttributes ra) {
        if (userService.changeGroup(userId,groupId)) {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_SUCCESS, "Cập nhật group user thành công!"));
        } else {
            ra.addFlashAttribute("msg", new Message(Constant.MESSAGE_TYPE_FAILURE, "Cập nhật group user thất bại!"));
        }
        return "redirect:/admin/user/employee";
    }

    @GetMapping("/{username}/update-permission")
    public String updatePermission(Model model, @PathVariable("username") Optional<String> username) {
        if (username.isPresent()) {
            User user = userService.getUserByUsername(username.get());
            if (user == null) {
                return "admin/error/page_404";
            }
            model.addAttribute("user", user);
            model.addAttribute("actions", actionService.getAllActionsByUserName(username.get()));
            return "admin/user/permission";
        }
        return "admin/error/page_404";
    }

    private List<Group> getListGroupsExceptOneGroup(String groupName) {
        return groupService.getAllGroup()
                .stream()
                .filter(item -> !groupName.equals(item.getName()))
                .collect(Collectors.toList());
    }
}
