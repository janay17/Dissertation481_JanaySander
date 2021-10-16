package com.sander.fantastic_food.mapper;

import com.sander.fantastic_food.MealPlan;
import com.sander.fantastic_food.data_model.MealPlanDataModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MealPlanMapper {
    MealPlanMapper INSTANCE = Mappers.getMapper(MealPlanMapper.class);

    MealPlan toMealPlan(MealPlanDataModel dataModel);

    MealPlanDataModel toMealPlanDataModel(MealPlan mealPlan);

    List<MealPlan> toMealPlans(List<MealPlanDataModel> dataModels);
}
