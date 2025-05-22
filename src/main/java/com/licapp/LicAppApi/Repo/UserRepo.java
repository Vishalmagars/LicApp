package com.licapp.LicAppApi.Repo;

import com.licapp.LicAppApi.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, ObjectId> {

    Optional<User> findByUsername(String username);
}
