package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.SubsistenceFee;

import java.util.List;

public interface SubsistenceFeeService {
    List<SubsistenceFee> getAllByMonthAndYear(Integer month, Integer year);

    List<SubsistenceFee> getAllByMonthAndYearAndArea(Integer month, Integer year, Integer areaId);

    List<SubsistenceFee> getSubsistenceFeeByRoomId(Integer id);

    List<SubsistenceFee> getSubsistenceFeeByRoomIdAndTime(Integer roomId, Integer month, Integer year);

    boolean editSubsistenceFee(SubsistenceFee subsistenceFee);

    boolean addSubsistenceFee(SubsistenceFee subsistenceFee);
}
