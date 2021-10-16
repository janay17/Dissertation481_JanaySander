package com.sander.fantastic_food.data_model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "UserProfile", schema = "dbo")
@AttributeOverride(name = "id", column = @Column(name = "ProfileId"))
@Getter
@Setter
public class UserProfileDataModel extends IdentifiableDataModel{
    private String email;
    private String passwordHash;
    private String weight;
    private String height;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "UserAllergy",
            joinColumns = @JoinColumn(name = "ProfileId"),
            inverseJoinColumns = @JoinColumn(name = "AllergyId"))
    private List<AllergyDataModel> userAllergies;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "UserMealplan",
            joinColumns = @JoinColumn(name = "ProfileId"),
            inverseJoinColumns = @JoinColumn(name = "MealplanId"))
    private List<MealPlanDataModel> userMealPlans;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "UserFavourite",
            joinColumns = @JoinColumn(name = "ProfileId"),
            inverseJoinColumns = @JoinColumn(name = "RecipeId"))
    private List<RecipeDataModel> userFavourites;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "UserRecipe",
            joinColumns = @JoinColumn(name = "ProfileId"),
            inverseJoinColumns = @JoinColumn(name = "RecipeId"))
    private List<RecipeDataModel> userRecipes;
}
