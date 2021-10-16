package com.sander.fantasticfood.Model;

import androidx.annotation.Nullable;

import java.util.List;

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

    public UserProfile(int id, String email, String passwordHash, String weight, String height,
                       List<Allergy> userAllergies, List<MealPlan> userMealPlans,
                       List<Recipe> userFavourites, List<Recipe> userRecipes) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.weight = weight;
        this.height = height;
        this.userAllergies = userAllergies;
        this.userMealPlans = userMealPlans;
        this.userFavourites = userFavourites;
        this.userRecipes = userRecipes;
    }

    public UserProfile(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public List<Allergy> getUserAllergies() {
        return userAllergies;
    }

    public void setUserAllergies(List<Allergy> userAllergies) {
        this.userAllergies = userAllergies;
    }

    public List<MealPlan> getUserMealPlans() {
        return userMealPlans;
    }

    public void setUserMealPlans(List<MealPlan> userMealPlans) {
        this.userMealPlans = userMealPlans;
    }

    public List<Recipe> getUserFavourites() {
        return userFavourites;
    }

    public void setUserFavourites(List<Recipe> userFavourites) {
        this.userFavourites = userFavourites;
    }

    public List<Recipe> getUserRecipes() {
        return userRecipes;
    }

    public void setUserRecipes(List<Recipe> userRecipes) {
        this.userRecipes = userRecipes;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        UserProfile other = (UserProfile) obj;
        if (this.getEmail() == other.getEmail()) {
            return true;
        } else {
            return false;
        }
    }
}
