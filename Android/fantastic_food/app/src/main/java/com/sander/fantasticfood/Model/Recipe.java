package com.sander.fantasticfood.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.List;

public class Recipe implements Parcelable {
    private int id;
    private String recipeName;
    private String instructions;
    private int preparationTime;
    private String difficulty;
    private String recipeImage;
    private List<Allergy> recipeAllergies;
    private List<MealPlan> recipeMealPlans;

    public Recipe(int id, String recipeName, String instructions, int preparationTime,
                  String difficulty, String recipeImage, List<Allergy> recipeAllergies,
                  List<MealPlan> recipeMealPlans) {
        this.id = id;
        this.recipeName = recipeName;
        this.instructions = instructions;
        this.preparationTime = preparationTime;
        this.difficulty = difficulty;
        this.recipeImage = recipeImage;
        this.recipeAllergies = recipeAllergies;
        this.recipeMealPlans = recipeMealPlans;
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        recipeName = in.readString();
        instructions = in.readString();
        preparationTime = in.readInt();
        difficulty = in.readString();
        recipeImage = in.readString();
        recipeAllergies = in.createTypedArrayList(Allergy.CREATOR);
        recipeMealPlans = in.createTypedArrayList(MealPlan.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public List<Allergy> getRecipeAllergies() {
        return recipeAllergies;
    }

    public void setRecipeAllergies(List<Allergy> recipeAllergies) {
        this.recipeAllergies = recipeAllergies;
    }

    public List<MealPlan> getRecipeMealPlans() {
        return recipeMealPlans;
    }

    public void setRecipeMealPlans(List<MealPlan> recipeMealPlans) {
        this.recipeMealPlans = recipeMealPlans;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Recipe other = (Recipe) obj;
        if (this.getId() == other.getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(recipeName);
        dest.writeString(instructions);
        dest.writeInt(preparationTime);
        dest.writeString(difficulty);
        dest.writeString(recipeImage);
        dest.writeTypedList(recipeAllergies);
        dest.writeTypedList(recipeMealPlans);
    }
}
