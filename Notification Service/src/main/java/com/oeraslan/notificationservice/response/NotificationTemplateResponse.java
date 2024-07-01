package com.oeraslan.notificationservice.response;

import com.oeraslan.notificationservice.enums.Condition;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationTemplateResponse {

    private Long id;
    private String genderCriteria;
    private Integer ageCriteria;
    private Condition condition;
    private String messageTemplate;
    private boolean deleted;
}
