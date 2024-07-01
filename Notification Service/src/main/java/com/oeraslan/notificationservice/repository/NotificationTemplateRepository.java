package com.oeraslan.notificationservice.repository;

import com.oeraslan.notificationservice.repository.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
}
