package com.sander.fantastic_food.mapper;

import com.sander.fantastic_food.Recipe;
import com.sander.fantastic_food.data_model.RecipeDataModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RecipeMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    Recipe toRecipe(RecipeDataModel dataModel);

    RecipeDataModel toRecipeDataModel(Recipe recipe);

    List<Recipe> toRecipes(List<RecipeDataModel> dataModels);
}
