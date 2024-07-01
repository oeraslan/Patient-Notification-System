package com.oeraslan.patientservice.kafka;

import com.oeraslan.patientservice.repository.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PatientProducer {

    private static final String TOPIC = "patients";

    @Autowired
    private KafkaTemplate<String, Patient> kafkaTemplate;

    public void sendPatient(Patient patient) {
        kafkaTemplate.send(TOPIC, patient);
    }
}

