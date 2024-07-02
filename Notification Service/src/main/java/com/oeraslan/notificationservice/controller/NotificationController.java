package com.oeraslan.notificationservice.controller;

import com.oeraslan.notificationservice.request.NotificationTemplateRequest;
import com.oeraslan.notificationservice.request.NotificationTemplateUpdateRequest;
import com.oeraslan.notificationservice.service.NotificationService;
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

@Slf4j
@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void createNotificationTemplate(@RequestBody NotificationTemplateRequest notificationTemplateRequest) {
        log.info("[{}][createNotificationTemplate] -> request: {}", this.getClass().getSimpleName(), notificationTemplateRequest);
        notificationService.createNotificationTemplate(notificationTemplateRequest);
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateNotificationTemplate(@RequestBody NotificationTemplateUpdateRequest updateRequest, @PathVariable Long id) {
        log.info("[{}][updateNotificationTemplate] -> request: {}", this.getClass().getSimpleName(), updateRequest);
        notificationService.updateNotificationTemplate(id, updateRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getNotificationTemplateById(@PathVariable Long id) {
        log.info("[{}][getNotificationTemplateById] -> request notification template by id: {}", this.getClass().getSimpleName(), id);
        return ResponseEntity.ok().body(notificationService.getNotificationTemplateById(id));
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAllNotificationTemplates() {
        log.info("[{}][getAllNotificationTemplates] -> request all notification templates", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(notificationService.getAllNotificationTemplates());
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNotificationTemplate(@PathVariable Long id) {
        log.info("[{}][deleteNotificationTemplate] -> request id: {}", this.getClass().getSimpleName(), id);
        notificationService.deleteNotificationTemplate(id);
    }

}
