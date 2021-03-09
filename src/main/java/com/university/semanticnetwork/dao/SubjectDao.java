package com.university.semanticnetwork.dao;

import com.university.semanticnetwork.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectDao extends JpaRepository<Subject, Long> {
}