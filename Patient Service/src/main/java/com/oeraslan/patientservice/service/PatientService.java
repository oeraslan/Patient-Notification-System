package com.oeraslan.patientservice.service;

import com.oeraslan.patientservice.repository.entity.Patient;
import com.oeraslan.patientservice.request.PatientCreateRequest;
import com.oeraslan.patientservice.request.PatientSearchRequest;
import com.oeraslan.patientservice.request.PatientUpdateRequest;
import com.oeraslan.patientservice.response.PatientResponse;

import java.util.List;

public interface PatientService {

    void createPatient(PatientCreateRequest createRequest);

    void updatePatient(Long id, PatientUpdateRequest updateRequest);

    void deletePatient(Long id);

    PatientResponse getPatientById(Long id);

    List<PatientResponse> getAllPatients();

    List<PatientResponse> getPatientBySearchParameters(PatientSearchRequest searchRequest);

}
