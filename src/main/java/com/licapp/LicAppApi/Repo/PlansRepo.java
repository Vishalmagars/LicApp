package com.licapp.LicAppApi.Repo;

import com.licapp.LicAppApi.Entity.Plans;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PlansRepo extends MongoRepository<Plans, ObjectId> {
    Optional<Plans> findByTitle(String title);

    void deleteByTitle(String title);
}