package com.licapp.LicAppApi.Repo;

import com.licapp.LicAppApi.Entity.Testimonials;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TestimonialsRepo extends MongoRepository<Testimonials, ObjectId> {
    Optional<Testimonials> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}