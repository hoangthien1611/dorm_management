package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.Cost;

import java.util.List;

public interface CostService {
    List<Cost> getAllByType(Integer type);
}
