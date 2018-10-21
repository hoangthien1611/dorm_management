package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.common.Headers;
import com.doan.dormmanagement.dto.DataResponse;
import com.doan.dormmanagement.model.Notification;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Notification> getNotificationByUserId(Integer userId) {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "notification/user/" + userId, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return (List<Notification>) data.getData();
        }
        return null;
    }

    @Override
    public List<Notification> getNotificationByNotifyId(Integer notifyId) {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "notification/user/read/" + notifyId, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return (List<Notification>) data.getData();
        }
        return null;
    }
}
