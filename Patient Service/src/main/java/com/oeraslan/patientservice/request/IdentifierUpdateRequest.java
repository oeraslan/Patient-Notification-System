package com.oeraslan.patientservice.request;

import lombok.Data;

@Data
public class IdentifierUpdateRequest {

    private Long id;
    private String type;
    private String value;

}
