package com.oeraslan.notificationservice.repository.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Data
public class Patient {
    private Long id;
    private String gender;
    private Date birthDate;
    private List<Contact> contacts;

    public int getAge() {
        LocalDate birthDateLocal = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(birthDateLocal, LocalDate.now()).getYears();
    }
}
