package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.dto.DataResponse;
import com.doan.dormmanagement.model.SubsistenceFee;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SubsistenceFeeServiceImpl implements SubsistenceFeeService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<SubsistenceFee> getAllSubsistenceFee() {
        return null;
    }

    @Override
    public List<SubsistenceFee> getSubsistenceFeeByRoomId(Integer id) {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "subsistence/room/" + id, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return (List<SubsistenceFee>) data.getData();
        }
        return null;
    }
}
