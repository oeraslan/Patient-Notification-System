package com.oeraslan.patientservice.repository;

import com.oeraslan.patientservice.repository.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> getPatientByIdAndDeletedFalse(Long id);

    @Query("SELECT p FROM Patient p WHERE " +
            "(:name IS NULL OR :name MEMBER OF p.names) AND " +
            "(:surname IS NULL OR p.surname = :surname) AND " +
            "(:gender IS NULL OR p.gender = :gender)")
    List<Patient> searchPatients(@Param("name") String name,
                                 @Param("surname") String surname,
                                 @Param("gender") String gender);

}
