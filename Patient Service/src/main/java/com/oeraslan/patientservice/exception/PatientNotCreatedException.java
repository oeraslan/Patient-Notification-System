package com.oeraslan.patientservice.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class PatientNotCreatedException extends RuntimeException {

    public PatientNotCreatedException(String message) {
        super("Patient not created");
        log.error("[PatientNotCreatedException] -> Patient not created message: {}", message);
    }
}
