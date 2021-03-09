package com.university.semanticnetwork.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectsRelationKey implements Serializable {
    @Column(name = "first_subject_id", nullable = false)
    private Long firstSubjectId;

    @Column(name = "second_subject_id", nullable = false)
    private Long secondSubjectId;

    @Column(name = "relation_id", nullable = false)
    private Long relationId;

}
