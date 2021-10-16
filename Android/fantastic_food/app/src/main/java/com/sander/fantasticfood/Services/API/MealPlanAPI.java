package com.sander.fantasticfood.Services.API;

import com.sander.fantasticfood.Model.MealPlan;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MealPlanAPI {
    @GET("/mealplans/{id}")
    Observable<MealPlan> getMealPlanByID(@Path("id") Integer id);

    @POST("/mealplans")
    Observable<MealPlan> saveNewMealPlan(@Body MealPlan mealPlan);

    @PUT("/mealplans")
    Observable<MealPlan> updateMealPlan(@Body MealPlan mealPlan);

    @GET("/mealplans")
    Observable<List<MealPlan>> getAllMealPlans();

    @GET("mealplans/search/{search}")
    Observable<List<MealPlan>> getBySearch(@Path("search") String searchText);
}
