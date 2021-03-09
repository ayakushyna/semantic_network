package com.university.semanticnetwork.dao;

import com.university.semanticnetwork.model.Relation;
import com.university.semanticnetwork.model.Subject;
import com.university.semanticnetwork.model.SubjectsRelation;
import com.university.semanticnetwork.model.SubjectsRelationKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectsRelationDao extends JpaRepository<SubjectsRelation, SubjectsRelationKey> {

    List<SubjectsRelation> findDistinctByOriginal(boolean original);
    List<SubjectsRelation> findDistinctByFirstSubject(Subject firstSubject);
    List<SubjectsRelation> findDistinctBySecondSubject(Subject secondSubject);
    List<SubjectsRelation> findDistinctByRelation(Relation relation);
    List<SubjectsRelation> findDistinctByFirstSubjectAndRelation(Subject firstSubject, Relation relation);
    List<SubjectsRelation> findDistinctBySecondSubjectAndRelation(Subject secondSubject, Relation relation);
    List<SubjectsRelation> findDistinctByFirstSubjectAndSecondSubject(Subject firstSubject, Subject secondSubject);
    boolean existsByFirstSubjectAndRelationAndSecondSubject(Subject firstSubject, Relation relation, Subject secondSubject);
}