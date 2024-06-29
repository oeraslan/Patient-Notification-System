package com.oeraslan.patientservice.request;

import lombok.Data;

@Data
public class IdentifierCreateRequest {

    private String type;
    private String value;

}
