package com.oeraslan.notificationservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oeraslan.notificationservice.enums.NotificationPreference;
import com.oeraslan.notificationservice.repository.model.Contact;
import com.oeraslan.notificationservice.repository.model.Patient;
import com.oeraslan.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientConsumer {

    private static final String TOPIC = "patient-notification";

    private final NotificationService notificationService;

    @KafkaListener(topics = TOPIC, groupId = "group_id")
    public void consume(String message) throws JsonProcessingException, ParseException {
        log.info("[{}][consume] -> message: {}", this.getClass().getSimpleName(), message);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> record = objectMapper.readValue(message, new TypeReference<Map<String, Object>>() {});

        Patient patient = new Patient();
        patient.setId(Long.parseLong(record.get("id").toString()));
        patient.setGender((String) record.get("gender"));
        patient.setBirthDate(formatter.parse(record.get("birthdate").toString()));
        List<Map<String, Object>> contactsMapList = (List<Map<String, Object>>) record.get("contacts");
        List<Contact> contacts = new ArrayList<>();
        for (Map<String, Object> contactMap : contactsMapList) {
            Contact contact = Contact.builder()
                    .type(NotificationPreference.valueOf((String) contactMap.get("type")))
                    .value((String) contactMap.get("value"))
                    .build();
            contacts.add(contact);
        }

        patient.setContacts(contacts);

        notificationService.evaluateAndAddToTarget(patient);

        log.info("[{}][consume] -> patient: {}", this.getClass().getSimpleName(), patient);
    }


}
