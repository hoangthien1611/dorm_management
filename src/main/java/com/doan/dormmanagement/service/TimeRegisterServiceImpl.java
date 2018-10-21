package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.common.Headers;
import com.doan.dormmanagement.dto.TimeRegisterDataResponse;
import com.doan.dormmanagement.model.TimeRegister;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TimeRegisterServiceImpl implements TimeRegisterService {
    private RestTemplate restTemplate = new RestTemplate();

    public TimeRegister getAllTimeRegisterByDate(Integer roomId) {
        TimeRegisterDataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "register/get-time-register/" + roomId, TimeRegisterDataResponse.class);

        if (data.getCode() == 200 && data.getData() != null) {
            return data.getData();
        }

        return null;
    }
}