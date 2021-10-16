package com.sander.fantasticfood.Services.Logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sander.fantasticfood.Model.Allergy;
import com.sander.fantasticfood.Model.MealPlan;
import com.sander.fantasticfood.Model.Recipe;
import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.Services.API.RecipeAPI;
import com.sander.fantasticfood.Services.Adapters.RecipeRecyclerAdapter;
import com.sander.fantasticfood.Services.RESTClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecipeLogic {

    private RecipeViewModel recipeViewModel;
    private Fragment fragment;
    private boolean basedOnPreferences;
    private Recipe currentRecipe;
    private UserProfile currentUser;

    public RecipeLogic(Fragment fragment, boolean basedOnPreferences, Context context) {
        this.fragment = fragment;
        this.recipeViewModel = ViewModelProviders.of(fragment).get(RecipeViewModel.class);
        this.basedOnPreferences = basedOnPreferences;

        this.currentUser = UserProfileLogic.getCurrentUser(context);
    }

    public RecipeLogic(Fragment fragment) {
        this.fragment = fragment;
        this.recipeViewModel = ViewModelProviders.of(fragment).get(RecipeViewModel.class);
    }

    public RecipeLogic() {

    }

    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }

    private List<Recipe> results;

    @SuppressLint("CheckResult")
    public void searchRecipes(Context context, String searchText, RecyclerView recyclerView) {

        int currentPageToView = recipeViewModel.getCurrentPage();

        RESTClient restClient = new RESTClient(context);
        RecipeAPI recipeAPI = restClient.getRecipeAPI();
        Observable<List<Recipe>> recipeObservable = recipeAPI.searchRecipes(searchText, currentPageToView);
        recipeObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipes -> {
                    recipeViewModel.setRecipes(recipes);
                    setUpRecyclerView(recyclerView, context, basedOnPreferences);
                }, exception -> {
                    Log.e("ERROR", "populateData: " + "ERROR " + exception.getMessage());
                });
    }

    private void setUpRecyclerView(RecyclerView recyclerView, Context context, boolean basedOnPreferences) {
        if (basedOnPreferences) {
            displayBasedOnPreferences(recyclerView, context);
        } else {
            RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(recipeViewModel.getRecipes(),
                    fragment, context);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        }
    }

    private void displayBasedOnPreferences(RecyclerView recyclerView, Context context) {
        List<Recipe> newResults = new ArrayList<>();
        for (Recipe recipe : this.recipeViewModel.getRecipes()) {
            boolean containsAllergens = false;
            for (Allergy allergy : currentUser.getUserAllergies()) {
                if (recipe.getRecipeAllergies().contains(allergy)) {
                    containsAllergens = true;
                }
            }
            boolean matchMealPlans = false;
            for (MealPlan mealPlan : currentUser.getUserMealPlans()) {
                if (recipe.getRecipeMealPlans().contains(mealPlan)) {
                    matchMealPlans = true;
                }
            }
            if (!containsAllergens && matchMealPlans) {
                newResults.add(recipe);
            }

        }
        RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(newResults, fragment, context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
    }

    public Recipe updateRecipeData(Context context, Recipe recipeData) {
        this.currentUser = UserProfileLogic.getCurrentUser(context);
        RESTClient restClient = new RESTClient(context);
        RecipeAPI recipeAPI = restClient.getRecipeAPI();
        Observable<Recipe> recipeObservable = recipeAPI.updateRecipe(recipeData);
        recipeObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipe -> {
                    this.currentRecipe = recipe;
                    new UserProfileLogic().getUserDataByEmail(context, this.currentUser.getEmail());
                }, exception -> {
                    Log.e("ERROR", "populateData: " + "ERROR " + exception.getMessage());
                });
        return null;
    }

    public Recipe saveNewRecipe(Context context, Recipe recipeData) {
        this.currentUser = UserProfileLogic.getCurrentUser(context);
        RESTClient restClient = new RESTClient(context);
        RecipeAPI recipeAPI = restClient.getRecipeAPI();
        Observable<Recipe> recipeObservable = recipeAPI.saveNewRecipe(recipeData);
        recipeObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipe -> {
                    this.currentRecipe = recipe;
                    this.currentUser.getUserRecipes().add(recipe);
                    new UserProfileLogic().updateUserData(context, currentUser);
                }, exception -> {
                    Log.e("ERROR", "populateData: " + "ERROR " + exception.getMessage());
                });
        return null;
    }

    public void deleteRecipe(Context context, Recipe recipeData) {

        this.currentUser = UserProfileLogic.getCurrentUser(context);

        RESTClient restClient = new RESTClient(context);
        RecipeAPI recipeAPI = restClient.getRecipeAPI();
        Observable<Boolean> recipeObservable = recipeAPI.deleteRecipe(recipeData.getId());
        recipeObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleted -> {
                    Log.i("DELETE", "deleteRecipe: Recipe with id " + recipeData.getId()
                            + " deleted = " + deleted.toString());
                    new UserProfileLogic().getUserDataByEmail(context, this.currentUser.getEmail());
                }, exception -> {
                    Log.e("ERROR", "populateData: " + "ERROR " + exception.getMessage());
                });
    }
}
