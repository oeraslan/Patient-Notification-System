package com.oeraslan.patientservice.service.impl;

import com.oeraslan.patientservice.exception.PatientAlreadyDeletedException;
import com.oeraslan.patientservice.exception.PatientNotCreatedException;
import com.oeraslan.patientservice.exception.PatientNotFoundException;
import com.oeraslan.patientservice.repository.PatientRepository;
import com.oeraslan.patientservice.repository.entity.Patient;
import com.oeraslan.patientservice.repository.mapper.PatientMapper;
import com.oeraslan.patientservice.request.PatientCreateRequest;
import com.oeraslan.patientservice.request.PatientSearchRequest;
import com.oeraslan.patientservice.request.PatientUpdateRequest;
import com.oeraslan.patientservice.response.PatientResponse;
import com.oeraslan.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    @Override
    public void createPatient(PatientCreateRequest createRequest) {
        log.debug("[{}][createPatient] -> request: {}", this.getClass().getSimpleName(), createRequest);

        try {
            Patient patient = patientMapper.createRequestToPatient(createRequest);
            Patient patientResponse = patientRepository.save(patient);
            log.debug("[{}][createPatient] -> patient created: {}", this.getClass().getSimpleName(), patientResponse);
        }
        catch (Exception e) {
            throw new PatientNotCreatedException(e.getMessage());
        }

    }

    @Override
    public PatientResponse getPatientById(Long id) {
        log.debug("[{}][getPatient] -> request id: {}", this.getClass().getSimpleName(), id);

        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));

        log.debug("[{}][getPatient] -> response patient: {}", this.getClass().getSimpleName(), patient);

        return patientMapper.patientToResponse(patient);
    }

    @Override
    public void updatePatient(Long id, PatientUpdateRequest updateRequest) {
        log.debug("[{}][updatePatient] -> request: {}", this.getClass().getSimpleName(), updateRequest);

        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));

        patientMapper.updateRequestToPatient(updateRequest, patient);

        Patient patientResponse = patientRepository.save(patient);

        log.debug("[{}][updatePatient] -> patient updated: {}", this.getClass().getSimpleName(), patientResponse);
    }

    @Override
    public void deletePatient(Long id) {
        log.debug("[{}][deletePatient] -> request id: {}", this.getClass().getSimpleName(), id);

        Patient patient = patientRepository.getPatientByIdAndDeletedFalse(id).orElseThrow(() -> new PatientAlreadyDeletedException(id));

        patient.setDeleted(true);
        patient.setPatientUpdatedDate(new Date());

        Patient patientResponse = patientRepository.save(patient);

        log.debug("[{}][deletePatient] -> patient deleted: {}", this.getClass().getSimpleName(), patientResponse);

    }

    @Override
    public List<PatientResponse> getAllPatients() {
        log.debug("[{}][getAllPatients] -> called", this.getClass().getSimpleName());

        return patientRepository.findAll().stream()
                .map(patientMapper::patientToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientResponse> getPatientBySearchParameters(PatientSearchRequest searchRequest) {
        log.debug("[{}][getPatientBySearchParameters] -> request: {}", this.getClass().getSimpleName(), searchRequest);
        return patientRepository.searchPatients(searchRequest.getName(), searchRequest.getSurname(), searchRequest.getGender()).stream()
                .map(patientMapper::patientToResponse)
                .collect(Collectors.toList());
    }

}
