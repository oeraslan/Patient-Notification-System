package com.oeraslan.patientservice.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oeraslan.patientservice.repository.entity.Contact;
import com.oeraslan.patientservice.repository.entity.Identifier;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class PatientUpdateRequest {

    private List<String> names;
    private String surname;
    private String notificationPreference;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthdate;
    private String gender;
    private Set<ContactUpdateRequest> contacts;
    private Set<IdentifierUpdateRequest> identifiers;
    private boolean deleted;

}
