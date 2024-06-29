package com.oeraslan.patientservice.repository.mapper;

import ch.qos.logback.core.util.StringUtil;
import com.oeraslan.patientservice.repository.PatientRepository;
import com.oeraslan.patientservice.repository.entity.Contact;
import com.oeraslan.patientservice.repository.entity.Identifier;
import com.oeraslan.patientservice.repository.entity.Patient;
import com.oeraslan.patientservice.request.ContactCreateRequest;
import com.oeraslan.patientservice.request.ContactUpdateRequest;
import com.oeraslan.patientservice.request.IdentifierCreateRequest;
import com.oeraslan.patientservice.request.IdentifierUpdateRequest;
import com.oeraslan.patientservice.request.PatientCreateRequest;
import com.oeraslan.patientservice.request.PatientUpdateRequest;
import com.oeraslan.patientservice.response.ContactResponse;
import com.oeraslan.patientservice.response.IdentifierResponse;
import com.oeraslan.patientservice.response.PatientResponse;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PatientMapper {

    public Patient createRequestToPatient(PatientCreateRequest request) {
        return Patient.builder()
                .names(request.getNames())
                .surname(request.getSurname())
                .notificationPreference(request.getNotificationPreference())
                .gender(request.getGender())
                .birthdate(request.getBirthdate())
                .deleted(false)
                .build();
    }

    public Contact createRequestToContact(ContactCreateRequest request, Patient patient) {
        return Contact.builder()
                .type(request.getType())
                .value(request.getValue())
                .patient(patient)
                .build();
    }

    public Identifier createRequestToIdentifier(IdentifierCreateRequest request, Patient patient) {
        return Identifier.builder()
                .type(request.getType())
                .value(request.getValue())
                .patient(patient)
                .build();
    }

    public void updateRequestToContact(ContactUpdateRequest request, Contact contact) {
        if(Objects.nonNull(request.getId())) {
            contact.setId(request.getId());
        }
        if(StringUtil.notNullNorEmpty(request.getType())) {
            contact.setType(request.getType());
        }
        if(StringUtil.notNullNorEmpty(request.getValue())) {
            contact.setValue(request.getValue());
        }
    }

    public void updateRequestToIdentifier(IdentifierUpdateRequest request, Identifier identifier) {
            if (Objects.nonNull(request.getId())) {
                identifier.setId(request.getId());
            }
            if(StringUtil.notNullNorEmpty(request.getType())) {
                identifier.setType(request.getType());
            }
            if(StringUtil.notNullNorEmpty(request.getValue())) {
                identifier.setValue(request.getValue());
            }
    }

    public void updateRequestToPatient(PatientUpdateRequest request, Patient patient) {
        if (Objects.nonNull(request.getNames())) {
            patient.setNames(request.getNames());
        }
        if (StringUtil.notNullNorEmpty(request.getSurname())) {
            patient.setSurname(request.getSurname());
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
        if (Objects.nonNull(request.isDeleted())) {
            patient.setDeleted(request.isDeleted());
        }

        patient.setPatientUpdatedDate(new Date());

    }

    public PatientResponse patientToResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .names(patient.getNames())
                .surname(patient.getSurname())
                .gender(patient.getGender())
                .notificationPreference(patient.getNotificationPreference())
                .birthdate(patient.getBirthdate())
                .contacts(patient.getContacts().stream().map(this::contactToResponse).collect(Collectors.toSet()))
                .identifiers(patient.getIdentifiers().stream().map(this::identifierToResponse).collect(Collectors.toSet()))
                .build();
    }

    public ContactResponse contactToResponse (Contact contact) {
        return ContactResponse.builder()
                .id(contact.getId())
                .type(contact.getType())
                .value(contact.getValue())
                .build();
    }

    public IdentifierResponse identifierToResponse(Identifier identifier) {
        return IdentifierResponse.builder()
                .id(identifier.getId())
                .type(identifier.getType())
                .value(identifier.getValue())
                .build();
    }

}
