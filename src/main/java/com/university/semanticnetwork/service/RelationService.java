package com.university.semanticnetwork.service;

import com.university.semanticnetwork.dao.RelationDao;
import com.university.semanticnetwork.model.Relation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelationService {

    private final RelationDao relationDao;

    public List<Relation> list() {
        return new LinkedList<>(relationDao.findAll());
    }

    public Relation find(Long id) {
        return relationDao.getOne(id);
    }

    public Relation create(Relation relation) {
        return relationDao.save(relation);
    }

    public void deleteAll() {
        relationDao.deleteAll();
    }
}
