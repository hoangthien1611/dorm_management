package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> getNotificationByUserId(Integer userId);

    List<Notification> getNotificationByNotifyId(Integer notifyId);

}
