package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.common.Headers;
import com.doan.dormmanagement.dto.DataResponse;
import com.doan.dormmanagement.model.User;
import org.springframework.http.*;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean getLogin(User user) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "user/login";

        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 200) {
            return true;
        }
        return false;
    }

    public List<User> getAllUsers() {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "user", DataResponse.class);

        if (data.getCode() == 200 && data.getData() != null) {
            List<User> users = (List<User>) data.getData();
            return users;
        }

        return null;
    }

    public List<User> getUsersbyRoomId(int roomId) {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "user/room/" + roomId, DataResponse.class);

        if (data.getCode() == 200 && data.getData() != null) {
            List<User> users = (List<User>) data.getData();
            return users;
        }

        return null;
    }

}
