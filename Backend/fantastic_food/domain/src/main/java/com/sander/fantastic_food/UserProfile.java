package com.sander.fantastic_food;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserProfile {
    private int id;
    private String email;
    private String passwordHash;
    private String weight;
    private String height;
    private List<Allergy> userAllergies;
    private List<MealPlan> userMealPlans;
    private List<Recipe> userFavourites;
    private List<Recipe> userRecipes;
}
