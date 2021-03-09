package com.university.semanticnetwork.validator;

import com.university.semanticnetwork.model.FileUpload;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return FileUpload.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        FileUpload fileUpload = (FileUpload) obj;

        MultipartFile file = fileUpload.getFile();

        if (file == null || file.getSize() == 0) {
            errors.rejectValue("file", "file.empty", "File is empty");
        }
    }
}
