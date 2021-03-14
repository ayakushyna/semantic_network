package com.university.semanticnetwork.service;

import com.university.semanticnetwork.dao.SubjectsRelationDao;
import com.university.semanticnetwork.model.SubjectsRelation;
import com.university.semanticnetwork.model.SubjectsRelationKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectsRelationService {

    private final SubjectsRelationDao subjectsRelationDao;

    public List<SubjectsRelation> list() {
        return new LinkedList<>(subjectsRelationDao.findDistinctByOriginal(true));
    }

    public SubjectsRelation create(SubjectsRelation subjectsRelation) {
        if (subjectsRelation.getId() == null) {
            subjectsRelation.setId(new SubjectsRelationKey());
        }
        return subjectsRelationDao.save(subjectsRelation);
    }

    public void deleteAll() {
        subjectsRelationDao.deleteAll();
    }
}
