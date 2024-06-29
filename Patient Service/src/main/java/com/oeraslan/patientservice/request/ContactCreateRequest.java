package com.oeraslan.patientservice.request;

import lombok.Data;

@Data
public class ContactCreateRequest {

    private String type;
    private String value;

}
