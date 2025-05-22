package com.licapp.LicAppApi.Service;

import com.licapp.LicAppApi.Entity.Plans;
import com.licapp.LicAppApi.Repo.PlansRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlansService {

    @Autowired
    PlansRepo plansRepo;

    public void savePlan(Plans plan) {
        plansRepo.save(plan);
    }

    public List<Plans> getPlans() {
        return plansRepo.findAll();
    }

    public Optional<Plans> getPlanByTitle(String title) {
        return plansRepo.findByTitle(title);
    }

    public Plans updateByTitle(String title, Plans updatedPlan) {
        Optional<Plans> optionalPlan = plansRepo.findByTitle(title);
        if (optionalPlan.isPresent()) {
            Plans existingPlan = optionalPlan.get();
            existingPlan.setTitle(updatedPlan.getTitle());
            existingPlan.setCategory(updatedPlan.getCategory());
            existingPlan.setDescription(updatedPlan.getDescription());
            existingPlan.setMinAge(updatedPlan.getMinAge());
            existingPlan.setMaxAge(updatedPlan.getMaxAge());
            existingPlan.setPolicyTerm(updatedPlan.getPolicyTerm());
            existingPlan.setPremiumRange(updatedPlan.getPremiumRange());
            existingPlan.setSumAssuredRange(updatedPlan.getSumAssuredRange());
            existingPlan.setMaturityBenefits(updatedPlan.getMaturityBenefits());
            existingPlan.setTaxBenefits(updatedPlan.getTaxBenefits());
            existingPlan.setUpdatedAt(updatedPlan.getUpdatedAt());
            return plansRepo.save(existingPlan);
        }
        return null;
    }

    public boolean deleteByTitle(String title) {
        Optional<Plans> optionalPlan = plansRepo.findByTitle(title);
        if (optionalPlan.isPresent()) {
            plansRepo.deleteByTitle(title);
            return true;
        }
        return false;
    }
}