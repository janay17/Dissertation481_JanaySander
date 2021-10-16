package com.sander.fantastic_food.repository;

import com.sander.fantastic_food.data_model.MealPlanDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlanDataModel, Integer> {

    List<MealPlanDataModel> findByMealplanNameContaining(String mealplanSearch);
}
