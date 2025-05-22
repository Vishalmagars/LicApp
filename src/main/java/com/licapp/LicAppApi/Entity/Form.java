package com.licapp.LicAppApi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "form")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Form {
    @Id
    private ObjectId id;
    private String name;
    private String email;
    private String number;
    private String message;
    private String planname;
}