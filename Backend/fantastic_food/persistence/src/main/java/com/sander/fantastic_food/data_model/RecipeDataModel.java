package com.sander.fantastic_food.data_model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Recipe", schema = "dbo")
@AttributeOverride(name = "id", column = @Column(name = "RecipeId"))
@Getter
@Setter
public class RecipeDataModel extends IdentifiableDataModel{
    private String recipeName;
    private String instructions;
    private int preparationTime;
    private String difficulty;
    private String recipeImage;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "RecipeAllergy",
            joinColumns = @JoinColumn(name = "RecipeId"),
            inverseJoinColumns = @JoinColumn(name = "AllergyId"))
    private List<AllergyDataModel> recipeAllergies;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "RecipeMealplan",
            joinColumns = @JoinColumn(name = "RecipeId"),
            inverseJoinColumns = @JoinColumn(name = "MealplanId"))
    private List<MealPlanDataModel> recipeMealPlans;

}
