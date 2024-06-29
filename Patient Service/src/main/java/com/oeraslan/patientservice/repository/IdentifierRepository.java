package com.oeraslan.patientservice.repository;

import com.oeraslan.patientservice.repository.entity.Identifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentifierRepository extends JpaRepository<Identifier, Long> {
}
