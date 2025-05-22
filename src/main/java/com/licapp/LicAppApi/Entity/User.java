package com.licapp.LicAppApi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String contorller;
    private String username;
    private String password;
    private String email;
    private List<Plans> plansList = new ArrayList<>();
    private List<Blog> blogList = new ArrayList<>();
    private List<Testimonials> testimonialsList = new ArrayList<>();
    private List<Form> formList = new ArrayList<>();
}