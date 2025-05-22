package com.licapp.LicAppApi.Controller;

import com.licapp.LicAppApi.Entity.Form;
import com.licapp.LicAppApi.Service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/form")
public class FormController {

    @Autowired
    private FormService formService;

    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : null;
    }

    @GetMapping
    public ResponseEntity<?> getForms() {
        return new ResponseEntity<>(formService.getForms(), HttpStatus.OK);
    }

    @PostMapping
    public void saveForm(@RequestBody Form form) {
        formService.saveForm(form);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getFormById(@PathVariable String id) {
//        Optional<Form> form = formService.getFormById(new ObjectId(id));
//        if (form.isPresent()) {
//            return new ResponseEntity<>(form.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>("Form not found", HttpStatus.NOT_FOUND);
//    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteForm(@PathVariable String email) {
        if (getAuthenticatedUsername() == null) {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.FORBIDDEN);
        }
        try {
            boolean deleted = formService.deleteByEmail(email);
            if (deleted) {
                return new ResponseEntity<>("Form deleted successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Form not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting form: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/test")
    public String test() {
        return "Form is working";
    }
}