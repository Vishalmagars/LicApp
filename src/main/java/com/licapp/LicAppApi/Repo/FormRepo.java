package com.licapp.LicAppApi.Repo;

import com.licapp.LicAppApi.Entity.Form;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FormRepo extends MongoRepository<Form, ObjectId> {
    Optional<Form> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}