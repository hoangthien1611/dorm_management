package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.dto.DataResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RoleUserServiceImpl implements RoleUserService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean changeStatusActionByUsernameAndActionId(String username, Integer actionId) {
        String url = BaseAPI.BASE_API_PREFIX + "role_user/change-status/" + username + "/" + actionId;
        DataResponse data = restTemplate.getForObject(url, DataResponse.class);
        if (data.getCode() == 200) {
            return true;
        }
        return false;
    }
}
