package com.university.semanticnetwork.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Relation {
    @Id
    @Column(name = "relation_id", nullable = false, unique = true)
    private Long relationId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Basic
    @Column(name = "relation_type", nullable = false)
    private Integer relationTypeValue;

    @Transient
    private RelationType relationType;

    @PostLoad
    void fillTransient() {
        if (relationTypeValue != null) {
            this.relationType = RelationType.fromValue(relationTypeValue);
        }
    }

    @PrePersist
    void fillPersistent() {
        if (relationType != null) {
            this.relationTypeValue = relationType.getValue();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
