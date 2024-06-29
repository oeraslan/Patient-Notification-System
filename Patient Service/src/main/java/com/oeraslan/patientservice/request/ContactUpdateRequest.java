package com.oeraslan.patientservice.request;

import lombok.Data;

@Data
public class ContactUpdateRequest {

    private Long id;
    private String type;
    private String value;

}
