package com.licapp.LicAppApi.Service;

import com.licapp.LicAppApi.Entity.Form;
import com.licapp.LicAppApi.Repo.FormRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    @Autowired
    private FormRepo formRepo;

    public void saveForm(Form form) {
        System.out.println("Saving form: " + form);
        Form savedForm = formRepo.save(form);
        System.out.println("Form saved: " + savedForm);
    }

    public List<Form> getForms() {
        return formRepo.findAll();
    }

    public Optional<Form> getFormById(ObjectId id) {
        return formRepo.findById(id);
    }

    public Optional<Form> getFormByEmail(String email) {
        return formRepo.findByEmail(email);
    }

    public boolean deleteByEmail(String email) {
        if (formRepo.existsByEmail(email)) {
            formRepo.deleteByEmail(email);
            return true;
        }
        return false;
    }
}