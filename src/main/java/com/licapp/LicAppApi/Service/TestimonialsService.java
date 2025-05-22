package com.licapp.LicAppApi.Service;

import com.licapp.LicAppApi.Entity.Testimonials;
import com.licapp.LicAppApi.Repo.TestimonialsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestimonialsService {

    @Autowired
    TestimonialsRepo testimonialsRepo;

    public void saveTestimonial(Testimonials testimonial) {
        testimonialsRepo.save(testimonial);
    }

    public List<Testimonials> getTestimonials() {
        return testimonialsRepo.findAll();
    }

    public Optional<Testimonials> getTestimonialByEmail(String email) {
        return testimonialsRepo.findByEmail(email);
    }

    public Testimonials updateByEmail(String email, Testimonials updatedTestimonial) {
        Optional<Testimonials> optionalTestimonial = testimonialsRepo.findByEmail(email);
        if (optionalTestimonial.isPresent()) {
            Testimonials existingTestimonial = optionalTestimonial.get();
            existingTestimonial.setName(updatedTestimonial.getName());
            existingTestimonial.setReview(updatedTestimonial.getReview());
            // Do not update email to maintain consistency with the URL path
            return testimonialsRepo.save(existingTestimonial);
        }
        return null;
    }

    public boolean deleteByEmail(String email) {
        if (testimonialsRepo.existsByEmail(email)) {
            testimonialsRepo.deleteByEmail(email);
            return true;
        }
        return false;
    }
}