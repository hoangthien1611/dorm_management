package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.dto.ActionDataResponse;
import com.doan.dormmanagement.dto.GroupDataResponse;
import com.doan.dormmanagement.model.Action;
import com.doan.dormmanagement.model.Group;
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
    public List<Action> getAllActionsByGroupId(Integer groupId) {
//        ActionDataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "user/get_group", ActionDataResponse.class);
//        if (data.getCode() == 200 && data.getData() != null) {
//            return data.getData();
//        }

        return null;
    }
}
