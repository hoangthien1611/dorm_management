package com.doan.dormmanagement.controller;

import com.doan.dormmanagement.model.Floor;
import com.doan.dormmanagement.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/floor")
public class AdminFloorController {

    @Autowired
    private FloorService floorService;

    @GetMapping("/area/{areaId}")
    @ResponseBody
    public List<Floor> getFloorsByAreaId(@PathVariable("areaId") Optional<String> id) {
        try {
            if (id.isPresent()) {
                Integer areaId = Integer.parseInt(id.get());
                List<Floor> list = floorService.getAllFloorsByAreaId(areaId);
                return list != null ? list : new ArrayList<>();
            }

            return new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public String addFloor(@Valid @ModelAttribute Floor floor) {
        if (floorService.addFloor(floor)) {
            return "OK";
        }
        return null;
    }

    @PostMapping("/change-status")
    @ResponseBody
    public String changeStatus(@RequestParam("floorId") Integer id, @RequestParam("stt") Integer stt) {
        if (floorService.changeStatus(id, stt)) {
            return "OK";
        }
        return null;
    }
}
