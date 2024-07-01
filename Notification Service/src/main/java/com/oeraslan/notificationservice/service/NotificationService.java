package com.oeraslan.notificationservice.service;

import com.oeraslan.notificationservice.repository.entity.NotificationTemplate;
import com.oeraslan.notificationservice.repository.model.Patient;
import com.oeraslan.notificationservice.request.NotificationTemplateRequest;
import com.oeraslan.notificationservice.request.NotificationTemplateUpdateRequest;
import com.oeraslan.notificationservice.response.NotificationTemplateResponse;

import java.util.List;

public interface NotificationService {

    public void createNotificationTemplate(NotificationTemplateRequest notificationTemplateRequest);
    public NotificationTemplateResponse getNotificationTemplateById(Long id);
    public List<NotificationTemplateResponse> getAllNotificationTemplates();
    public void updateNotificationTemplate(Long id, NotificationTemplateUpdateRequest notificationTemplateUpdateRequest);
    public void deleteNotificationTemplate(Long id);
    public void evaluateAndAddToTarget(Patient patient);

}
