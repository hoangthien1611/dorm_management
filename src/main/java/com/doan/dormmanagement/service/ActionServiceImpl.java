package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.dto.ActionDataResponse;
import com.doan.dormmanagement.model.Action;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ActionServiceImpl implements ActionService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Action> getAllActionsByUserName(String username) {
        ActionDataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "user/get-action-by-name/" + username, ActionDataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return data.getData();
        }
        return null;
    }

    @Override
    public List<Action> getAllActions() {
        ActionDataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "user/get_action", ActionDataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return data.getData();
        }
        return null;
    }
}
