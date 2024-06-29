package com.oeraslan.patientservice.request;

import lombok.Data;

@Data
public class PatientSearchRequest {

    private String name;
    private String surname;
    private String gender;

}
