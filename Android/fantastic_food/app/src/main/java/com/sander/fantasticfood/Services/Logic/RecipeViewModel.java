package com.sander.fantasticfood.Services.Logic;

import androidx.lifecycle.ViewModel;

import com.sander.fantasticfood.Model.Recipe;

import java.util.List;

public class RecipeViewModel extends ViewModel {
    private int currentPage = 0;

    private List<Recipe> recipes;
    private List<Recipe> recipesBasedOnMealPlans;
    private List<Recipe> recipesBasedOnAllergies;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Recipe> getRecipesBasedOnMealPlans() {
        return recipesBasedOnMealPlans;
    }

    public void setRecipesBasedOnMealPlans(List<Recipe> recipesBasedOnMealPlans) {
        this.recipesBasedOnMealPlans = recipesBasedOnMealPlans;
    }

    public List<Recipe> getRecipesBasedOnAllergies() {
        return recipesBasedOnAllergies;
    }

    public void setRecipesBasedOnAllergies(List<Recipe> recipesBasedOnAllergies) {
        this.recipesBasedOnAllergies = recipesBasedOnAllergies;
    }
}
