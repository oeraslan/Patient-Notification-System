package com.oeraslan.notificationservice.repository.model;

import com.oeraslan.notificationservice.enums.NotificationPreference;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contact {

    private NotificationPreference type;
    private String value;

}
