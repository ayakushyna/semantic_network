package com.university.semanticnetwork.service;

import com.university.semanticnetwork.exception.WrongSequenceException;
import com.university.semanticnetwork.model.Relation;
import com.university.semanticnetwork.model.Subject;
import com.university.semanticnetwork.model.SubjectsRelation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private final SemanticManager semanticManager;
    private final SubjectService subjectService;
    private final RelationService relationService;
    private final SubjectsRelationService subjectsRelationService;

    public void uploadFile(MultipartFile file) {
        int step = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(file.getBytes()), StandardCharsets.UTF_8
                )
        )) {
            while (reader.ready()) {
                String line = reader.readLine();

                if (line.contains("#1")) {
                    if (step == 0) {
                        step = 1;
                    } else {
                        throw new WrongSequenceException();
                    }
                } else if (line.contains("#2")) {
                    if (step == 1) {
                        step = 2;
                    } else {
                        throw new WrongSequenceException();
                    }
                } else if (line.contains("#3")) {
                    if (step == 2) {
                        step = 3;
                    } else {
                        throw new WrongSequenceException();
                    }
                } else {
                    if (step == 1) {

                        String[] parts = line.split(":");
                        if (parts.length == 2) {
                            Subject subject = Subject.builder()
                                    .subjectId(Long.valueOf(parts[0].trim()))
                                    .name(parts[1].trim())
                                    .build();

                            subjectService.create(subject);
                        } else {
                            throw new WrongSequenceException();
                        }

                    } else if (step == 2) {

                        String[] parts = line.split(":");
                        if (parts.length == 3) {
                            Relation relation = Relation.builder()
                                    .relationId(Long.valueOf(parts[0].trim()))
                                    .name(parts[1].trim())
                                    .relationTypeValue(Integer.valueOf(parts[2].trim()))
                                    .build();

                            relationService.create(relation);
                        } else {
                            throw new WrongSequenceException();
                        }

                    } else if (step == 3) {

                        String[] parts = line.split(":");
                        if (parts.length == 3) {
                            SubjectsRelation subjectsRelation = SubjectsRelation.builder()
                                    .firstSubject(subjectService.find(Long.valueOf(parts[0].trim())))
                                    .relation(relationService.find(Long.valueOf(parts[1].trim())))
                                    .secondSubject(subjectService.find(Long.valueOf(parts[2].trim())))
                                    .original(true)
                                    .build();

                            subjectsRelationService.create(subjectsRelation);
                        } else {
                            throw new WrongSequenceException();
                        }

                    } else {
                        throw new WrongSequenceException();
                    }
                }

            }

            semanticManager.fit();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
