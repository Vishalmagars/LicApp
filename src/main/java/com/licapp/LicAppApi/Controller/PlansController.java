package com.licapp.LicAppApi.Controller;

import com.licapp.LicAppApi.Entity.Plans;
import com.licapp.LicAppApi.Service.PlansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plans")
public class PlansController {

    @Autowired
    private PlansService plansService;

    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : null;
    }

    @GetMapping
    public ResponseEntity<?> getPlans() {
        return new ResponseEntity<>(plansService.getPlans(), HttpStatus.OK);
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> getPlanByTitle(@PathVariable String title) {
        Optional<Plans> plan = plansService.getPlanByTitle(title);
        if (plan.isPresent()) {
            return new ResponseEntity<>(plan.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Plan not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addplan")
    public ResponseEntity<String> addPlan(@RequestBody Plans plan) {
        String authUsername = getAuthenticatedUsername();
        if (authUsername == null) {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.FORBIDDEN);
        }
        if (plan.getTitle() == null || plan.getTitle().trim().isEmpty()) {
            return new ResponseEntity<>("Plan title cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        try {
            plansService.savePlan(plan);
            return new ResponseEntity<>("Plan added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding plan: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{title}")
    public ResponseEntity<String> updatePlan(@PathVariable String title, @RequestBody Plans updatedPlan) {
        if (getAuthenticatedUsername() == null) {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.FORBIDDEN);
        }
        if (updatedPlan.getTitle() == null || updatedPlan.getTitle().trim().isEmpty()) {
            return new ResponseEntity<>("Plan title cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        try {
            Plans plan = plansService.updateByTitle(title, updatedPlan);
            if (plan != null) {
                return new ResponseEntity<>("Plan updated successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Plan not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating plan: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<String> deletePlan(@PathVariable String title) {
        if (getAuthenticatedUsername() == null) {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.FORBIDDEN);
        }
        try {
            boolean deleted = plansService.deleteByTitle(title);
            if (deleted) {
                return new ResponseEntity<>("Plan deleted successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Plan not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting plan: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/test")
    public String test() {
        return "Plans is working";
    }
}