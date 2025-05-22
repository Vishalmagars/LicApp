package com.licapp.LicAppApi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("blogs")
@Data
@AllArgsConstructor
public class Blog {

    @Id
    private String id;
    private String title;
    private String description;
    private String img;
    private List<String> tags;
    private String content;
    private String author;
    private String postdate;
    private String controller;

}
