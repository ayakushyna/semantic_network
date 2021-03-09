package com.university.semanticnetwork.controller;

import com.university.semanticnetwork.model.FileUpload;
import com.university.semanticnetwork.model.InputForm;
import com.university.semanticnetwork.model.Relation;
import com.university.semanticnetwork.model.Subject;
import com.university.semanticnetwork.model.SubjectsRelation;
import com.university.semanticnetwork.service.RelationService;
import com.university.semanticnetwork.service.SemanticManager;
import com.university.semanticnetwork.service.SubjectService;
import com.university.semanticnetwork.service.SubjectsRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SubjectsRelationService subjectsRelationService;
    private final SemanticManager semanticManager;
    private final SubjectService subjectService;
    private final RelationService relationService;

    @GetMapping("/")
    public String getHomePage(Model model) {
        setHomeModel(model);
        model.addAttribute("inputForm", new InputForm());
        model.addAttribute("answerList", new ArrayList<>());

        return "home";
    }

    private void setHomeModel(Model model) {
        model.addAttribute("fileUpload", new FileUpload());

        model.addAttribute("subjectsRelationList", subjectsRelationService.list());
        model.addAttribute("subjectList", subjectService.list());
        model.addAttribute("relationList", relationService.list());
    }

    @PostMapping("/predict")
    public String predict(@ModelAttribute("inputForm") InputForm inputForm,
                          Model model) {
        List<String> answerList = semanticManager.predict(inputForm);

        setHomeModel(model);
        model.addAttribute("answerList", answerList);

        return "home";
    }
}
