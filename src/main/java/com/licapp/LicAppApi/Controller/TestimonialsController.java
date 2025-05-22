package com.licapp.LicAppApi.Controller;

import com.licapp.LicAppApi.Entity.Testimonials;
import com.licapp.LicAppApi.Service.TestimonialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/testimonials")
public class TestimonialsController {

    @Autowired
    private TestimonialsService testimonialsService;

    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : null;
    }

    @GetMapping
    public ResponseEntity<?> getTestimonials() {
        return new ResponseEntity<>(testimonialsService.getTestimonials(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getTestimonialByEmail(@PathVariable String email) {
        Optional<Testimonials> testimonial = testimonialsService.getTestimonialByEmail(email);
        if (testimonial.isPresent()) {
            return new ResponseEntity<>(testimonial.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Testimonial not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addtestimonial")
    public ResponseEntity<String> addTestimonial(@RequestBody Testimonials testimonial) {
        String authUsername = getAuthenticatedUsername();
        if (authUsername == null) {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.FORBIDDEN);
        }
        if (testimonial.getName() == null || testimonial.getName().trim().isEmpty()) {
            return new ResponseEntity<>("Testimonial name cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        if (testimonial.getEmail() == null || testimonial.getEmail().trim().isEmpty()) {
            return new ResponseEntity<>("Testimonial email cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        if (testimonial.getReview() == null || testimonial.getReview().trim().isEmpty()) {
            return new ResponseEntity<>("Testimonial review cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        try {
            testimonialsService.saveTestimonial(testimonial);
            return new ResponseEntity<>("Testimonial added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding testimonial: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> updateTestimonial(@PathVariable String email, @RequestBody Testimonials updatedTestimonial) {
        if (getAuthenticatedUsername() == null) {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.FORBIDDEN);
        }
        if (updatedTestimonial.getName() == null || updatedTestimonial.getName().trim().isEmpty()) {
            return new ResponseEntity<>("Testimonial name cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        if (updatedTestimonial.getEmail() == null || updatedTestimonial.getEmail().trim().isEmpty()) {
            return new ResponseEntity<>("Testimonial email cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        if (updatedTestimonial.getReview() == null || updatedTestimonial.getReview().trim().isEmpty()) {
            return new ResponseEntity<>("Testimonial review cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        try {
            Testimonials testimonial = testimonialsService.updateByEmail(email, updatedTestimonial);
            if (testimonial != null) {
                return new ResponseEntity<>("Testimonial updated successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Testimonial not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating testimonial: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteTestimonial(@PathVariable String email) {
        if (getAuthenticatedUsername() == null) {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.FORBIDDEN);
        }
        try {
            boolean deleted = testimonialsService.deleteByEmail(email);
            if (deleted) {
                return new ResponseEntity<>("Testimonial deleted successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Testimonial not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting testimonial: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/test")
    public String test() {
        return "Testimonials is working";
    }
}