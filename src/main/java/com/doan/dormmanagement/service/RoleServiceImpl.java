package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.common.Headers;
import com.doan.dormmanagement.dto.DataResponse;
import com.doan.dormmanagement.model.Action;
import com.doan.dormmanagement.model.Role;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RoleServiceImpl implements RoleService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean addRole(Role role) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<Role> entity = new HttpEntity<>(role, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "role/add";
        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 207) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delRole(Integer groupId, Integer actionId) {
        String url = BaseAPI.BASE_API_PREFIX + "user/delete_action/" + actionId + "/" + groupId;
        DataResponse data = restTemplate.getForObject(url, DataResponse.class);
        if (data.getCode() == 209) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addActionToGroup(Integer groupId, Integer actionId) {
        String url = BaseAPI.BASE_API_PREFIX + "user/add-action-for-group-available/" + actionId + "/" + groupId;
        DataResponse data = restTemplate.getForObject(url, DataResponse.class);
        if (data.getCode() == 208) {
            return true;
        }
        return false;
    }
}
