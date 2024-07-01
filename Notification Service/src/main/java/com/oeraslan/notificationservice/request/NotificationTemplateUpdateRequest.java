package com.oeraslan.notificationservice.request;

import com.oeraslan.notificationservice.enums.Condition;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationTemplateUpdateRequest {

    private Long id;
    private String genderCriteria;
    private Integer ageCriteria;
    private Condition condition;
    private String messageTemplate;

}
