package com.oeraslan.patientservice.service.impl;

import com.oeraslan.patientservice.exception.PatientAlreadyDeletedException;
import com.oeraslan.patientservice.exception.PatientNotCreatedException;
import com.oeraslan.patientservice.exception.PatientNotFoundException;
import com.oeraslan.patientservice.kafka.PatientProducer;
import com.oeraslan.patientservice.repository.ContactRepository;
import com.oeraslan.patientservice.repository.IdentifierRepository;
import com.oeraslan.patientservice.repository.PatientRepository;
import com.oeraslan.patientservice.repository.entity.Contact;
import com.oeraslan.patientservice.repository.entity.Identifier;
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

    private final ContactRepository contactRepository;

    private final IdentifierRepository identifierRepository;

    private final PatientMapper patientMapper;

    private final PatientProducer patientProducer;

    @Override
    public void createPatient(PatientCreateRequest createRequest) {
        log.info("[{}][createPatient] -> request: {}", this.getClass().getSimpleName(), createRequest);

        try {
            Patient patient = patientMapper.createRequestToPatient(createRequest);
            Patient patientResponse = patientRepository.save(patient);

            patientResponse.setContacts(createRequest.getContacts().stream()
                    .map(contactCreateRequest -> {
                        Contact contact = patientMapper.createRequestToContact(contactCreateRequest, patientResponse);
                        return contactRepository.save(contact);
                    })
                    .collect(Collectors.toSet()));
            patientResponse.setIdentifiers(createRequest.getIdentifiers().stream()
                    .map(identifierCreateRequest -> {
                        Identifier identifier = patientMapper.createRequestToIdentifier(identifierCreateRequest, patientResponse);
                        return identifierRepository.save(identifier);
                    })
                    .collect(Collectors.toSet()));


            patientRepository.save(patientResponse);
            PatientResponse kafkaResponse = patientMapper.patientToResponse(patientResponse);
            patientProducer.sendPatient(kafkaResponse);
            log.info("[{}][createPatient] -> patient created and sent: {}", this.getClass().getSimpleName(), patientResponse);
        } catch (Exception e) {
            throw new PatientNotCreatedException(e.getMessage());
        }
    }

    @Override
    public PatientResponse getPatientById(Long id) throws PatientNotFoundException {
        log.debug("[{}][getPatient] -> request id: {}", this.getClass().getSimpleName(), id);

        return patientMapper.patientToResponse(patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id)));
    }

    @Override
    public void updatePatient(Long id, PatientUpdateRequest updateRequest) {
        log.debug("[{}][updatePatient] -> request: {}", this.getClass().getSimpleName(), updateRequest);

        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));

        patient.setContacts(updateRequest.getContacts().stream()
                .map(contactUpdateRequest -> {
                    Contact contact = contactRepository.findById(contactUpdateRequest.getId()).get();
                    patientMapper.updateRequestToContact(contactUpdateRequest, contact);
                    return contactRepository.save(contact);
                })
                .collect(Collectors.toSet()));
        patient.setIdentifiers(updateRequest.getIdentifiers().stream()
                .map(identifierUpdateRequest -> {
                    Identifier identifier = identifierRepository.findById(identifierUpdateRequest.getId()).get();
                    patientMapper.updateRequestToIdentifier(identifierUpdateRequest, identifier);
                    return identifierRepository.save(identifier);
                })
                .collect(Collectors.toSet()));

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
