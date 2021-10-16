package com.sander.fantastic_food.facade;

import com.sander.fantastic_food.MealPlan;
import com.sander.fantastic_food.data_model.MealPlanDataModel;
import com.sander.fantastic_food.mapper.MealPlanMapper;
import com.sander.fantastic_food.repository.MealPlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MealPlanFacade implements CRUD<MealPlan, Integer>{

    private final MealPlanRepository mealPlanRepository;
    Logger LOGGER = LoggerFactory.getLogger(MealPlanFacade.class);

    @Autowired
    public MealPlanFacade(MealPlanRepository mealPlanRepository) {
        this.mealPlanRepository = mealPlanRepository;
    }

    @Override
    public MealPlan findById(Integer id) {
        Optional<MealPlanDataModel> datamodels = mealPlanRepository.findById(id);
        if(datamodels.isPresent()) {
            return MealPlanMapper.INSTANCE.toMealPlan(datamodels.get());
        } else {
            LOGGER.warn("Meal plan not found");
            return null;
        }
    }

    @Override
    public List<MealPlan> findAll() {
        List<MealPlanDataModel> dataModels = mealPlanRepository.findAll();
        if(dataModels.isEmpty()) {
            LOGGER.warn("No meal plans found");
            return null;
        } else {
            return MealPlanMapper.INSTANCE.toMealPlans(dataModels);
        }
    }

    @Override
    public MealPlan saveChanges(MealPlan value) {
        if(value != null) {
            return MealPlanMapper.INSTANCE.toMealPlan(mealPlanRepository.save(MealPlanMapper.INSTANCE.toMealPlanDataModel(value)));
        } else {
            LOGGER.warn("The meal plan could not be saved");
            return null;
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        mealPlanRepository.deleteById(id);
        if(findById(id) != null) {
            LOGGER.warn("Meal plan not deleted");
            return false;
        } else {
            return true;
        }
    }

    public List<MealPlan> findByMealplanNameContaining(String mealPlanName) {
        List<MealPlanDataModel> dataModels = mealPlanRepository.findByMealplanNameContaining(mealPlanName);
        if(dataModels.isEmpty()) {
            LOGGER.warn("No meal plans found");
            return null;
        } else {
            return MealPlanMapper.INSTANCE.toMealPlans(dataModels);
        }
    }
}
