package com.sander.fantastic_food;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Recipe {
    private int id;
    private String recipeName;
    private String instructions;
    private int preparationTime;
    private String difficulty;
    private String recipeImage;
    private List<Allergy> recipeAllergies;
    private List<MealPlan> recipeMealPlans;


    @Override
    public boolean equals(Object obj) {
        Recipe other = (Recipe) obj;
        return this.getId() == other.getId();
    }
}
