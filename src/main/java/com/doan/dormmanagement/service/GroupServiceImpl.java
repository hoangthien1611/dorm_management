package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.common.Headers;
import com.doan.dormmanagement.dto.DataResponse;
import com.doan.dormmanagement.dto.GroupDataResponse;
import com.doan.dormmanagement.model.Group;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Group> getAllGroup() {
        GroupDataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "user/get_group", GroupDataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return data.getData();
        }

        return null;
    }

    @Override
    public boolean addGroup(Group group) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<Group> entity = new HttpEntity<>(group, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "user/add_group";

        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 208) {
            return true;
        }
        return false;
    }

    @Override
    public boolean editGroup(Group group) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<Group> entity = new HttpEntity<>(group, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "user/edit_group";

        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 207) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delGroup(Integer groupId) {
        String url = BaseAPI.BASE_API_PREFIX + "user/delete_group/" + groupId;
        DataResponse data = restTemplate.getForObject(url, DataResponse.class);
        if (data.getCode() == 209) {
            return true;
        }
        return false;
    }
}
