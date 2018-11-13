package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.dto.DataResponse;
import com.doan.dormmanagement.model.Cost;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CostServiceImpl implements CostService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Cost> getAllByType(Integer type) {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "cost/type-cost/" + type, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return (List<Cost>) data.getData();
        }
        return null;
    }
}
