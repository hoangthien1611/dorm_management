package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.Area;

import java.util.List;

public interface AreaService {
    List<Area> getAllAreas();

    boolean editArea(Area a);

    boolean addArea(Area a);

    boolean changeStatus(Integer areaId, Integer status);
}
