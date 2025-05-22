package com.licapp.LicAppApi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("plans")
@Data
@AllArgsConstructor
public class Plans {
    @Id
    private ObjectId id;
    private String contorller;
    private String title;
    private String category;
    private String description;
    private String minAge;
    private String maxAge;
    private String policyTerm;
    private String premiumRange;
    private String sumAssuredRange;
    private String maturityBenefits;
    private String taxBenefits; // Use LocalDateTime if you want type safety
    private String updatedAt;
    private String content;
}
