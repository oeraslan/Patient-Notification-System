package com.oeraslan.patientservice.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactResponse {
    private Long id;
    private String type;
    private String value;
}
