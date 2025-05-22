package com.licapp.LicAppApi.Repo;

import com.licapp.LicAppApi.Entity.Blog;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BlogRepo extends MongoRepository<Blog, ObjectId> {
    Optional<Blog> findByTitle(String title);

    void deleteByTitle(String title);
}