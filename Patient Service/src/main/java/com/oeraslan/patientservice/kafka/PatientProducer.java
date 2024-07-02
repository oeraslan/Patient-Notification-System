package com.oeraslan.patientservice.kafka;

import com.oeraslan.patientservice.repository.entity.Patient;
import com.oeraslan.patientservice.response.PatientResponse;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PatientProducer {

    private static final String TOPIC = "patient-notification";

    @Autowired
    private KafkaTemplate<String, PatientResponse> kafkaTemplate;

    public void sendPatient(PatientResponse patient) throws SerializationException {
        kafkaTemplate.send(TOPIC, patient);
    }
}

