package com.university.semanticnetwork.dao;

import com.university.semanticnetwork.model.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationDao extends JpaRepository<Relation, Long> {
}
