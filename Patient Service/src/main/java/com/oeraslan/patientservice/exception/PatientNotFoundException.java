package com.oeraslan.patientservice.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(Long id) {
        super("Patient not found with id: " + id);
        log.error("[PatientNotFoundException] -> Patient not found with id: {}", id);
    }

}
