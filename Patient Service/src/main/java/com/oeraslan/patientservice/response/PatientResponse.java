package com.oeraslan.patientservice.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class PatientResponse {

    private Long id;
    private List<String> names;
    private String surname;
    private String notificationPreference;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthdate;
    private String gender;
    private Set<ContactResponse> contacts;
    private Set<IdentifierResponse> identifiers;

}
