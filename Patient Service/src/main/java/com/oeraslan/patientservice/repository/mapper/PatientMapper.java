package com.oeraslan.patientservice.repository.mapper;

import ch.qos.logback.core.util.StringUtil;
import com.oeraslan.patientservice.repository.entity.Patient;
import com.oeraslan.patientservice.request.PatientCreateRequest;
import com.oeraslan.patientservice.request.PatientUpdateRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class PatientMapper {

    public Patient createRequestToPatient(PatientCreateRequest request) {
        return Patient.builder()
                .names(request.getNames())
                .surname(request.getSurname())
                .notificationPreference(request.getNotificationPreference())
                .gender(request.getGender())
                .birthdate(request.getBirthdate())
                .contacts(request.getContacts())
                .identifiers(request.getIdentifiers())
                .deleted(false)
                .build();
    }

    public void updateRequestToPatient(PatientUpdateRequest request, Patient patient) {
        if (Objects.nonNull(request.getNames())) {
            patient.setNames(request.getNames());
        }
        if (StringUtil.notNullNorEmpty(request.getSurName())) {
            patient.setSurname(request.getSurName());
        }
        if (StringUtil.notNullNorEmpty(request.getNotificationPreference())) {
            patient.setNotificationPreference(request.getNotificationPreference());
        }
        if (Objects.nonNull(request.getBirthdate())) {
            patient.setBirthdate(request.getBirthdate());
        }
        if (StringUtil.notNullNorEmpty(request.getGender())) {
            patient.setGender(request.getGender());
        }
        if (Objects.nonNull(request.getContacts())) {
            patient.setContacts(request.getContacts());
        }
        if (Objects.nonNull(request.getIdentifiers())) {
            patient.setIdentifiers(request.getIdentifiers());
        }
        if (Objects.nonNull(request.isDeleted())) {
            patient.setDeleted(request.isDeleted());
        }

        patient.setPatientUpdatedDate(new Date());

    }


}
