package com.sander.fantastic_food.repository;

import com.sander.fantastic_food.data_model.AllergyDataModel;
import com.sander.fantastic_food.data_model.MealPlanDataModel;
import com.sander.fantastic_food.data_model.RecipeDataModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeDataModel, Integer> {

    Page<RecipeDataModel> findAll(Pageable page);
    Page<RecipeDataModel> findByRecipeNameContaining(String searchText, Pageable page);
    Page<RecipeDataModel> findByRecipeAllergiesContaining(AllergyDataModel allergy, Pageable page);
    Page<RecipeDataModel> findByRecipeMealPlansContaining(MealPlanDataModel mealPlan, Pageable page);
}
