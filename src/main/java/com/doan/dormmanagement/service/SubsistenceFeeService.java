package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.SubsistenceFee;

import java.util.List;

public interface SubsistenceFeeService {
    List<SubsistenceFee> getSubsistenceFeeByRoomIdWithDate(Integer roomId, Integer month, Integer year);

    List<SubsistenceFee> getAllByMonthAndYear(Integer month, Integer year);

    List<SubsistenceFee> getSubsistenceFeeByRoomId(Integer id);

    boolean editSubsistenceFee(SubsistenceFee subsistenceFee);
}
