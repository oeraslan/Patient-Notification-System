package com.oeraslan.notificationservice.repository.entity;

import com.oeraslan.notificationservice.enums.Condition;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@Table(name = "notification_template", schema = "notification_management")
@AllArgsConstructor
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String genderCriteria;
    private Integer ageCriteria;
    private Condition condition;
    private String messageTemplate;
    private boolean deleted;

}
