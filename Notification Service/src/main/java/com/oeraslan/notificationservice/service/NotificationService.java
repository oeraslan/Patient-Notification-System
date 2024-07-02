package com.oeraslan.notificationservice.service;

import com.oeraslan.notificationservice.repository.entity.NotificationTemplate;
import com.oeraslan.notificationservice.repository.model.Patient;
import com.oeraslan.notificationservice.request.NotificationTemplateRequest;
import com.oeraslan.notificationservice.request.NotificationTemplateUpdateRequest;
import com.oeraslan.notificationservice.response.NotificationTemplateResponse;

import java.util.List;

public interface NotificationService {

    void createNotificationTemplate(NotificationTemplateRequest notificationTemplateRequest);

    NotificationTemplateResponse getNotificationTemplateById(Long id);

    List<NotificationTemplateResponse> getAllNotificationTemplates();

    void updateNotificationTemplate(Long id, NotificationTemplateUpdateRequest notificationTemplateUpdateRequest);

    void deleteNotificationTemplate(Long id);

    void evaluateAndAddToTarget(Patient patient);

}
