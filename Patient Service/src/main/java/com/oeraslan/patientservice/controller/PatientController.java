package com.oeraslan.patientservice.controller;

import com.oeraslan.patientservice.repository.mapper.PatientMapper;
import com.oeraslan.patientservice.request.PatientCreateRequest;
import com.oeraslan.patientservice.request.PatientUpdateRequest;
import com.oeraslan.patientservice.response.PatientResponse;
import com.oeraslan.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void createPatient(@RequestBody PatientCreateRequest createRequest) {
        log.debug("[{}][createPatient] -> request: {}", this.getClass().getSimpleName(), createRequest);
        patientService.createPatient(createRequest);
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public void updatePatient(@RequestBody PatientUpdateRequest updateRequest, @PathVariable Long id) {
        log.debug("[{}][updatePatient] -> request: {}", this.getClass().getSimpleName(), updateRequest);
        patientService.updatePatient(id, updateRequest);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deletePatient(@PathVariable Long id) {
        log.debug("[{}][deletePatient] -> request id: {}", this.getClass().getSimpleName(), id);
        patientService.deletePatient(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPatientById(@PathVariable Long id) {
        log.debug("[{}][getPatientById] -> request id: {}", this.getClass().getSimpleName(), id);
        return ResponseEntity.ok().body(patientService.getPatientById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        log.debug("[{}][getAllPatients] -> request all patient", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(patientService.getAllPatients());
    }

}
