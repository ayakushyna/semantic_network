package com.university.semanticnetwork.service;

import com.university.semanticnetwork.dao.SubjectDao;
import com.university.semanticnetwork.model.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectDao subjectDao;

    public List<Subject> list() {
        return new LinkedList<>(subjectDao.findAll());
    }

    public Subject find(Long id) {
        return subjectDao.getOne(id);
    }

    public Subject create(Subject subject) {
        return subjectDao.save(subject);
    }
}
