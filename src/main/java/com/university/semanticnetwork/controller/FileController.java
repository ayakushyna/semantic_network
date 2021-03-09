package com.university.semanticnetwork.controller;

import com.university.semanticnetwork.model.FileUpload;
import com.university.semanticnetwork.service.FileService;
import com.university.semanticnetwork.validator.FileUploadValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final FileUploadValidator fileUploadValidator;
    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadFile(@ModelAttribute("fileUpload") FileUpload fileUpload,
                             BindingResult bindingResult) {
        fileUploadValidator.validate(fileUpload, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/";
        }

        MultipartFile file = fileUpload.getFile();
        log.info("Request contains, File: " + file.getOriginalFilename());
        fileService.uploadFile(file);

        return "redirect:/";
    }
}
