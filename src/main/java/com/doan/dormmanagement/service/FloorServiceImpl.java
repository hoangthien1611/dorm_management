package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.common.Headers;
import com.doan.dormmanagement.dto.DataResponse;
import com.doan.dormmanagement.model.Floor;
import org.springframework.http.*;
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

    @Override
    public boolean addFloor(Floor floor) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<Floor> entity = new HttpEntity<>(floor, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "floor/add-one";

        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 208) {
            return true;
        }
        return false;
    }

    @Override
    public boolean changeStatus(Integer floorId, Integer status) {
        String url = BaseAPI.BASE_API_PREFIX + "floor/change-status/" + floorId + "/" + status;
        DataResponse data = restTemplate.getForObject(url, DataResponse.class);
        if (data.getCode() == 207 && data.getData() != null) {
            return true;
        }
        return false;
    }
}
