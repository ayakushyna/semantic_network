package com.university.semanticnetwork.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "subjects_relation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SubjectsRelation {
    @EmbeddedId
    private SubjectsRelationKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("firstSubjectId")
    @JoinColumn(name = "first_subject_id", referencedColumnName = "subject_id")
    private Subject firstSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("secondSubjectId")
    @JoinColumn(name = "second_subject_id", referencedColumnName = "subject_id")
    private Subject secondSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("relationId")
    @JoinColumn(name = "relation_id", referencedColumnName = "relation_id")
    private Relation relation;

    @Column(name = "original")
    private boolean original = false;

    @Override
    public String toString() {
        return firstSubject.getName() + " " + relation.getName() + " " + secondSubject.getName();
    }
}
