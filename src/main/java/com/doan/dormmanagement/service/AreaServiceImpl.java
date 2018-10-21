package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.common.Headers;
import com.doan.dormmanagement.model.Area;
import com.doan.dormmanagement.dto.DataResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Area> getAllAreas() {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "areas", DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            List<Area> areas = (List<Area>) data.getData();
            return areas;
        }
        return null;
    }

    @Override
    public boolean editArea(Area area) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<Area> entity = new HttpEntity<>(area, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "areas/edit/" + area.getId();

        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 200) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addArea(Area area) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<Area> entity = new HttpEntity<>(area, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "areas/add";

        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody().getCode() == 200) {
            return true;
        }
        return false;
    }

}
