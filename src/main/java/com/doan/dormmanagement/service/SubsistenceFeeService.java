package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.SubsistenceFee;

import java.util.List;

public interface SubsistenceFeeService {
    List<SubsistenceFee> getAllSubsistenceFee();

    List<SubsistenceFee> getSubsistenceFeeByRoomId(Integer id);
}
