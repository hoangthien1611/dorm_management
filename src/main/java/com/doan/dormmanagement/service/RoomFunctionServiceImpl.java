package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.dto.DataResponse;
import com.doan.dormmanagement.model.RoomFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RoomFunctionServiceImpl implements RoomFunctionService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<RoomFunction> getAllRoomFunction() {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "room/function", DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return (List<RoomFunction>) data.getData();
        }
        return null;
    }
}
