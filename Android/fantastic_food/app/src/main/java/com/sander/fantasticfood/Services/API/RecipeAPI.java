package com.sander.fantasticfood.Services.API;

import com.sander.fantasticfood.Model.Allergy;
import com.sander.fantasticfood.Model.MealPlan;
import com.sander.fantasticfood.Model.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RecipeAPI {
    @GET("/recipes/{id}")
    Observable<Recipe> getRecipeByID(@Path("id") Integer id);

    @POST("/recipes")
    Observable<Recipe> saveNewRecipe(@Body Recipe recipe);

    @PUT("/recipes")
    Observable<Recipe> updateRecipe(@Body Recipe recipe);

    @DELETE("/recipes/{id}")
    Observable<Boolean> deleteRecipe(@Path("id") Integer id);

    @GET("/recipes/search/{search}/{page}")
    Observable<List<Recipe>> searchRecipes(@Path("search") String searchText,
                                           @Path("page") Integer page);

    @GET("/recipes/allergies/{page}")
    Observable<List<Recipe>> searchRecipesByAllergy(@Path("page") Integer page,
                                                    @Body Allergy allergy);

    @GET("/recipes/mealplans/{page}")
    Observable<List<Recipe>> searchRecipesByMealPlan(@Path("page") Integer page,
                                                    @Body MealPlan mealPlan);

}
