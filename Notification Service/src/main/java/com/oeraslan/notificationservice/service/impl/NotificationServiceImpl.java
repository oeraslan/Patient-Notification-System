package com.oeraslan.notificationservice.service.impl;

import com.oeraslan.notificationservice.repository.NotificationTemplateRepository;
import com.oeraslan.notificationservice.repository.TargetPatientRepository;
import com.oeraslan.notificationservice.repository.entity.NotificationTemplate;
import com.oeraslan.notificationservice.repository.entity.TargetPatient;
import com.oeraslan.notificationservice.repository.model.Patient;
import com.oeraslan.notificationservice.request.NotificationTemplateRequest;
import com.oeraslan.notificationservice.request.NotificationTemplateUpdateRequest;
import com.oeraslan.notificationservice.response.NotificationTemplateResponse;
import com.oeraslan.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationTemplateRepository notificationTemplateRepository;
    private final TargetPatientRepository targetPatientRepository;

    @Override
    public void createNotificationTemplate(NotificationTemplateRequest notificationTemplateRequest) {
        log.info("[{}][createNotificationTemplate] -> request: {}", this.getClass().getSimpleName(), notificationTemplateRequest);
        NotificationTemplate notificationTemplate = NotificationTemplate.builder()
                .ageCriteria(notificationTemplateRequest.getAgeCriteria())
                .genderCriteria(notificationTemplateRequest.getGenderCriteria())
                .condition(notificationTemplateRequest.getCondition())
                .messageTemplate(notificationTemplateRequest.getMessageTemplate())
                .deleted(false)
                .build();
        notificationTemplateRepository.save(notificationTemplate);
        log.info("[{}][createNotificationTemplate] -> template created: {}", this.getClass().getSimpleName(), notificationTemplate);
    }

    @Override
    public NotificationTemplateResponse getNotificationTemplateById(Long id) {
        log.info("[{}][getNotificationTemplateById] -> request notification template by id: {}", this.getClass().getSimpleName(), id);
        return notificationTemplateRepository.findById(id)
                .map(notificationTemplate -> NotificationTemplateResponse.builder()
                        .id(notificationTemplate.getId())
                        .ageCriteria(notificationTemplate.getAgeCriteria())
                        .genderCriteria(notificationTemplate.getGenderCriteria())
                        .condition(notificationTemplate.getCondition())
                        .messageTemplate(notificationTemplate.getMessageTemplate())
                        .deleted(notificationTemplate.isDeleted())
                        .build())
                .orElse(null);
    }

    @Override
    public List<NotificationTemplateResponse> getAllNotificationTemplates() {
        log.info("[{}][getAllNotificationTemplates] -> request all notification templates", this.getClass().getSimpleName());
        return notificationTemplateRepository.findAll().stream()
                .map(notificationTemplate -> NotificationTemplateResponse.builder()
                        .id(notificationTemplate.getId())
                        .ageCriteria(notificationTemplate.getAgeCriteria())
                        .genderCriteria(notificationTemplate.getGenderCriteria())
                        .condition(notificationTemplate.getCondition())
                        .messageTemplate(notificationTemplate.getMessageTemplate())
                        .deleted(notificationTemplate.isDeleted())
                        .build())
                .toList();
    }

    @Override
    public void updateNotificationTemplate(Long id, NotificationTemplateUpdateRequest notificationTemplateUpdateRequest) {
        log.info("[{}][updateNotificationTemplate] -> update request: {}", this.getClass().getSimpleName(), notificationTemplateUpdateRequest);

        NotificationTemplate notificationTemplate = notificationTemplateRepository.findById(id).orElseThrow();
        if (ObjectUtils.isNotEmpty(notificationTemplateUpdateRequest.getAgeCriteria())) {
            notificationTemplate.setAgeCriteria(notificationTemplateUpdateRequest.getAgeCriteria());
        }
        if (ObjectUtils.isNotEmpty(notificationTemplateUpdateRequest.getGenderCriteria())) {
            notificationTemplate.setGenderCriteria(notificationTemplateUpdateRequest.getGenderCriteria());
        }
        if (ObjectUtils.isNotEmpty(notificationTemplateUpdateRequest.getCondition())) {
            notificationTemplate.setCondition(notificationTemplateUpdateRequest.getCondition());
        }
        if (ObjectUtils.isNotEmpty(notificationTemplateUpdateRequest.getMessageTemplate())) {
            notificationTemplate.setMessageTemplate(notificationTemplateUpdateRequest.getMessageTemplate());
        }
        notificationTemplateRepository.save(notificationTemplate);

        log.info("[{}][updateNotificationTemplate] -> template updated: {}", this.getClass().getSimpleName(), notificationTemplate);
    }

    @Override
    public void deleteNotificationTemplate(Long id) {
        log.info("[{}][deleteNotificationTemplate] -> delete request id: {}", this.getClass().getSimpleName(), id);

        NotificationTemplate notificationTemplate = notificationTemplateRepository.findById(id).orElseThrow();
        notificationTemplate.setDeleted(true);
        notificationTemplateRepository.save(notificationTemplate);

        log.info("[{}][deleteNotificationTemplate] -> template deleted: {}", this.getClass().getSimpleName(), notificationTemplate);
    }

    @Override
    public void evaluateAndAddToTarget(Patient patient) {
        log.info("[{}][evaluateAndAddToTarget] -> patient: {}", this.getClass().getSimpleName(), patient);

        List<NotificationTemplate> templates = notificationTemplateRepository.findAll();
        for (NotificationTemplate template : templates) {
            if (matchesCriteria(template, patient)) {
                TargetPatient targetPatient = new TargetPatient();
                targetPatient.setPatientId(patient.getId());
                targetPatient.setTemplateId(template.getId());
                targetPatientRepository.save(targetPatient);
            }
        }
    }

    private boolean matchesCriteria(NotificationTemplate template, Patient patient) {
        boolean matches = true;
        if (template.getGenderCriteria() != null) {
            matches &= template.getGenderCriteria().equalsIgnoreCase(patient.getGender());
        }
        if (template.getAgeCriteria() != null) {
            matches &= template.getAgeCriteria() <= patient.getAge();
        }
        return matches;
    }

}
