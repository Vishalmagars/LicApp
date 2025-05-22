package com.licapp.LicAppApi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "testimonials")
@Data
@AllArgsConstructor
public class Testimonials {
    @Id
    private ObjectId id;

    @NonNull
    private String name;

    @NonNull
    private String review;

    @NonNull
    private String email;
}