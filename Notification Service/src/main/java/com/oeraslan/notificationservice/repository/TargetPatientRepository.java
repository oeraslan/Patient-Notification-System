package com.oeraslan.notificationservice.repository;

import com.oeraslan.notificationservice.repository.entity.TargetPatient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetPatientRepository extends JpaRepository<TargetPatient, Long> {

}
