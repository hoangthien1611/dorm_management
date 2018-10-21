package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.TimeRegister;

import java.util.List;

public interface TimeRegisterService {

    TimeRegister getAllTimeRegisterByDate(Integer roomId);
}
