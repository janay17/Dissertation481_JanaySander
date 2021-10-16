package com.sander.fantastic_food.facade;

import com.sander.fantastic_food.Allergy;
import com.sander.fantastic_food.MealPlan;
import com.sander.fantastic_food.Recipe;
import com.sander.fantastic_food.UserProfile;
import com.sander.fantastic_food.data_model.RecipeDataModel;
import com.sander.fantastic_food.mapper.AllergyMapper;
import com.sander.fantastic_food.mapper.MealPlanMapper;
import com.sander.fantastic_food.mapper.RecipeMapper;
import com.sander.fantastic_food.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RecipeFacade implements CRUD<Recipe, Integer>{

    private final RecipeRepository recipeRepository;
    private final UserProfileFacade userProfileFacade;
    Logger LOGGER = LoggerFactory.getLogger(RecipeFacade.class);

    @Autowired
    public RecipeFacade(RecipeRepository recipeRepository, UserProfileFacade userProfileFacade) {
        this.recipeRepository = recipeRepository;
        this.userProfileFacade = userProfileFacade;
    }

    @Override
    public Recipe findById(Integer id) {
        Optional<RecipeDataModel> dataModel = recipeRepository.findById(id);
        if(dataModel.isPresent()) {
            return RecipeMapper.INSTANCE.toRecipe(dataModel.get());
        } else {
            LOGGER.warn("Recipe not found");
            return null;
        }
    }

    @Override
    public List<Recipe> findAll() {
        return findAll("*", 0);
    }

    public List<Recipe> findAll(String searchText, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 10);
        Page<RecipeDataModel> entryPage;
        if(searchText.equals("*")){
            entryPage = this.recipeRepository.findAll(page);
        }
        else {
            entryPage = this.recipeRepository.findByRecipeNameContaining(searchText, page);
        }
        return RecipeMapper.INSTANCE.toRecipes(entryPage.get().collect(Collectors.toList()));
    }

    public List<Recipe> findByAllergy(Allergy allergy, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 10);
        Page<RecipeDataModel> entryPage;
        entryPage = recipeRepository.findByRecipeAllergiesContaining(AllergyMapper.INSTANCE.toAllergyDataModel(allergy), page);
        return RecipeMapper.INSTANCE.toRecipes(entryPage.get().collect(Collectors.toList()));
    }

    public List<Recipe> findByMealPlan(MealPlan mealplan, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 10);
        Page<RecipeDataModel> entryPage;
        entryPage = recipeRepository.findByRecipeMealPlansContaining(MealPlanMapper.INSTANCE.toMealPlanDataModel(mealplan), page);
        return RecipeMapper.INSTANCE.toRecipes(entryPage.get().collect(Collectors.toList()));
    }

    @Override
    public Recipe saveChanges(Recipe value) {
        if(value != null) {
            return RecipeMapper.INSTANCE.toRecipe(recipeRepository.save(RecipeMapper.INSTANCE.toRecipeDataModel(value)));
        } else {
            LOGGER.warn("The recipe could not be saved");
            return null;
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        Recipe recipeToDelete = findById(id);
        if(recipeToDelete != null) {
            for(UserProfile user : userProfileFacade.findAll()) {
                user.getUserFavourites().remove(recipeToDelete);
                user.getUserRecipes().remove(recipeToDelete);
                userProfileFacade.saveChanges(user);
            }

            recipeRepository.deleteById(id);
            if(findById(id) != null) {
                LOGGER.warn("Recipe not deleted");
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
