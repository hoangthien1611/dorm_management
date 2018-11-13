package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.Floor;

import java.util.List;

public interface FloorService {
    List<Floor> getAllFloorsByAreaId(Integer areaId);
}
