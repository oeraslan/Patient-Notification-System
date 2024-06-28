package com.oeraslan.patientservice.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class PatientAlreadyDeletedException extends RuntimeException {

    public PatientAlreadyDeletedException(Long id) {
        super("Patient already deleted with id: " + id);
        log.error("[PatientAlreadyDeletedException] -> Patient already deleted with id: {}", id);
    }

}
