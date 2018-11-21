package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.common.Headers;
import com.doan.dormmanagement.dto.DataResponse;
import com.doan.dormmanagement.dto.RegisterRoomDataResponse;
import com.doan.dormmanagement.model.RegisterRoom;
import com.doan.dormmanagement.model.Room;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RegisterRoomServiceImpl implements RegisterRoomService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<RegisterRoom> getAllByRoomId(Integer roomId) {
        RegisterRoomDataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "register/room/" + roomId, RegisterRoomDataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return data.getData();
        }
        return null;
    }

    @Override
    public List<RegisterRoom> getAllByAreaId(Integer areaId) {
        RegisterRoomDataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "register/area/" + areaId, RegisterRoomDataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return data.getData();
        }
        return null;
    }

    @Override
    public List<RegisterRoom> getAllByFloorId(Integer floorId) {
        RegisterRoomDataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "register/floor/" + floorId, RegisterRoomDataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return data.getData();
        }
        return null;
    }

    @Override
    public boolean acceptAll(Integer[] intArr) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<Integer[]> entity = new HttpEntity<>(intArr, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "register/accept";
        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 200) {
            return true;
        }
        return false;
    }
}
