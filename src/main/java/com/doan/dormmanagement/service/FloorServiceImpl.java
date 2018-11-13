package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.dto.DataResponse;
import com.doan.dormmanagement.model.Floor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FloorServiceImpl implements FloorService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Floor> getAllFloorsByAreaId(Integer areaId) {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "floor/" + areaId, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            List<Floor> list = (List<Floor>) data.getData();
            return list;
        }
        return null;
    }
}
