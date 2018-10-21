package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.common.Headers;
import com.doan.dormmanagement.dto.DataResponse;
import com.doan.dormmanagement.model.RegisterRoom;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RegisterRoomServiceImpl implements RegisterRoomService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean addRegisterRoom(RegisterRoom registerRoom) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<RegisterRoom> entity = new HttpEntity<>(registerRoom, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "register/add";

        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody().getCode() == 200) {
            return true;
        }
        return false;
    }

}
