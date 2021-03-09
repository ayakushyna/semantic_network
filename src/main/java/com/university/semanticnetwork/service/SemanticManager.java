package com.university.semanticnetwork.service;

import com.university.semanticnetwork.dao.RelationDao;
import com.university.semanticnetwork.dao.SubjectDao;
import com.university.semanticnetwork.dao.SubjectsRelationDao;
import com.university.semanticnetwork.model.InputForm;
import com.university.semanticnetwork.model.Relation;
import com.university.semanticnetwork.model.RelationType;
import com.university.semanticnetwork.model.Subject;
import com.university.semanticnetwork.model.SubjectsRelation;
import com.university.semanticnetwork.model.SubjectsRelationKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SemanticManager {

    private final SubjectDao subjectDao;
    private final RelationDao relationDao;
    private final SubjectsRelationDao subjectsRelationDao;

    List<SubjectsRelation> getAllKnowledge() {
        return subjectsRelationDao.findAll();
    }

    public void fit() {
        List<SubjectsRelation> subjectsRelationList = subjectsRelationDao.findAll();

        fitRelations(subjectsRelationList);
    }

    private void fitRelations(List<SubjectsRelation> subjectsRelationList) {
        List<SubjectsRelation> fittedSubjectsRelationList = new ArrayList<>();

        for (SubjectsRelation subjectsRelation : subjectsRelationList) {
            if (subjectsRelation.getRelation().getRelationTypeValue().equals(RelationType.HAS_PART.getValue())) {
                for (SubjectsRelation childSubjectsRelation : subjectsRelationList) {
                    if (childSubjectsRelation.getFirstSubject().equals(subjectsRelation.getSecondSubject()) &&
                            childSubjectsRelation.getRelation().getRelationTypeValue().equals(RelationType.IS_A.getValue())) {
                        fittedSubjectsRelationList.add(SubjectsRelation.builder()
                                .firstSubject(subjectsRelation.getFirstSubject())
                                .relation(childSubjectsRelation.getRelation())
                                .secondSubject(childSubjectsRelation.getSecondSubject())
                                .build()
                        );
                    } else if (childSubjectsRelation.getFirstSubject().equals(subjectsRelation.getSecondSubject()) &&
                            childSubjectsRelation.getRelation().getRelationTypeValue().equals(RelationType.HAS_PART.getValue())) {
                        fittedSubjectsRelationList.add(SubjectsRelation.builder()
                                .firstSubject(subjectsRelation.getFirstSubject())
                                .relation(childSubjectsRelation.getRelation())
                                .secondSubject(childSubjectsRelation.getSecondSubject())
                                .build()
                        );
                    }
                }
            }

            if (subjectsRelation.getRelation().getRelationTypeValue().equals(RelationType.IS_A.getValue())) {
                for (SubjectsRelation childSubjectsRelation : subjectsRelationList) {
                    if (childSubjectsRelation.getFirstSubject().equals(subjectsRelation.getSecondSubject())) {
                        fittedSubjectsRelationList.add(SubjectsRelation.builder()
                                .firstSubject(subjectsRelation.getFirstSubject())
                                .relation(childSubjectsRelation.getRelation())
                                .secondSubject(childSubjectsRelation.getSecondSubject())
                                .build()
                        );
                    }
                }
            }
        }

        if (!fittedSubjectsRelationList.isEmpty()) {

            boolean newRelations = false;

            for (SubjectsRelation subjectsRelation: fittedSubjectsRelationList) {
                if (!subjectsRelationDao.existsByFirstSubjectAndRelationAndSecondSubject(
                        subjectsRelation.getFirstSubject(),
                        subjectsRelation.getRelation(),
                        subjectsRelation.getSecondSubject())) {
                    subjectsRelation.setId(new SubjectsRelationKey());
                    subjectsRelationDao.save(subjectsRelation);
                    newRelations = true;
                }
            }

            if (newRelations) {
                List<SubjectsRelation> newSubjectsRelationList = subjectsRelationDao.findAll();
                fitRelations(newSubjectsRelationList);
            }
        }
    }

    List<SubjectsRelation> getByFirstSubject(Subject firstSubject) {
        return subjectsRelationDao.findDistinctByFirstSubject(firstSubject);
    }

    List<SubjectsRelation> getBySecondSubject(Subject secondSubject) {
        return subjectsRelationDao.findDistinctBySecondSubject(secondSubject);
    }

    List<SubjectsRelation> getByRelation(Relation relation) {
        return subjectsRelationDao.findDistinctByRelation(relation);
    }

    List<Subject> getSecondSubjects(Subject firstSubject, Relation relation) {
        List<Subject> secondSubjects = new ArrayList<>();

        subjectsRelationDao.findDistinctByFirstSubjectAndRelation(firstSubject, relation)
                .forEach(subjectsRelation -> secondSubjects.add(subjectsRelation.getSecondSubject()));

        return secondSubjects;
    }

    List<Subject> getFirstSubjects(Subject secondSubject, Relation relation) {
        List<Subject> firstSubjects = new ArrayList<>();

        subjectsRelationDao.findDistinctBySecondSubjectAndRelation(secondSubject, relation)
                .forEach(subjectsRelation -> firstSubjects.add(subjectsRelation.getFirstSubject()));

        return firstSubjects;
    }

    List<Relation> getRelations(Subject firstSubject, Subject secondSubject) {
        List<Relation> relations = new ArrayList<>();

        subjectsRelationDao.findDistinctByFirstSubjectAndSecondSubject(firstSubject, secondSubject)
                .forEach(subjectsRelation -> relations.add(subjectsRelation.getRelation()));

        return relations;
    }

    boolean isKnowledgeExist(Subject firstSubject, Relation relation, Subject secondSubject) {
        return subjectsRelationDao.existsByFirstSubjectAndRelationAndSecondSubject(
                firstSubject, relation, secondSubject
        );
    }

    public List<String> predict(InputForm inputForm) {
        String firstSubject = inputForm.getFirstSubject();
        String relation = inputForm.getRelation();
        String secondSubject = inputForm.getSecondSubject();

        List<String> answerList = new ArrayList<>();

        if (firstSubject.equals("?") && relation.equals("?") && secondSubject.equals("?")) {
            answerList = getAllKnowledge()
                    .stream()
                    .map(SubjectsRelation::toString)
                    .collect(Collectors.toList());;
        }
        else if (!firstSubject.equals("?") && !relation.equals("?") && secondSubject.equals("?")) {

            answerList = getSecondSubjects(
                    subjectDao.findById(Long.valueOf(firstSubject)).orElse(null),
                    relationDao.findById(Long.valueOf(firstSubject)).orElse(null))
                    .stream()
                    .map(Subject::toString)
                    .collect(Collectors.toList());

        } else if (!firstSubject.equals("?") && relation.equals("?") && !secondSubject.equals("?")) {

            answerList = getRelations(
                    subjectDao.findById(Long.valueOf(firstSubject)).orElse(null),
                    subjectDao.findById(Long.valueOf(secondSubject)).orElse(null))
                    .stream()
                    .map(Relation::toString)
                    .collect(Collectors.toList());

        } else if (firstSubject.equals("?") && !relation.equals("?") && !secondSubject.equals("?")) {

            answerList = getFirstSubjects(
                    subjectDao.findById(Long.valueOf(secondSubject)).orElse(null),
                    relationDao.findById(Long.valueOf(relation)).orElse(null) )
                    .stream()
                    .map(Subject::toString)
                    .collect(Collectors.toList());

        } else if (!firstSubject.equals("?") && relation.equals("?") && secondSubject.equals("?")) {

            answerList = getByFirstSubject(
                    subjectDao.findById(Long.valueOf(firstSubject)).orElse(null) )
                    .stream()
                    .map(SubjectsRelation::toString)
                    .collect(Collectors.toList());

        } else if (firstSubject.equals("?") && !relation.equals("?") && secondSubject.equals("?")) {

            answerList = getByRelation(
                    relationDao.findById(Long.valueOf(relation)).orElse(null) )
                    .stream()
                    .map(SubjectsRelation::toString)
                    .collect(Collectors.toList());

        } else if (firstSubject.equals("?") && relation.equals("?") && !secondSubject.equals("?")) {

            answerList = getBySecondSubject(
                    subjectDao.findById(Long.valueOf(secondSubject)).orElse(null) )
                    .stream()
                    .map(SubjectsRelation::toString)
                    .collect(Collectors.toList());

        } else if (!firstSubject.equals("?") && !relation.equals("?") && !secondSubject.equals("?")) {

            if (isKnowledgeExist(
                    subjectDao.findById(Long.valueOf(firstSubject)).orElse(null),
                    relationDao.findById(Long.valueOf(relation)).orElse(null),
                    subjectDao.findById(Long.valueOf(secondSubject)).orElse(null))) {
                answerList.add("Yes");
            } else {
                answerList.add("Does not know");
            }

        }

        return answerList;
    }
}
