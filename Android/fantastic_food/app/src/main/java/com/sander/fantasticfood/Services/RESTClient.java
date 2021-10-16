package com.sander.fantasticfood.Services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sander.fantasticfood.Services.API.AllergyAPI;
import com.sander.fantasticfood.Services.API.MealPlanAPI;
import com.sander.fantasticfood.Services.API.RecipeAPI;
import com.sander.fantasticfood.Services.API.UserProfileAPI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RESTClient {
    private final AllergyAPI allergyAPI;
    private final MealPlanAPI mealPlanAPI;
    private final RecipeAPI recipeAPI;
    private final UserProfileAPI userProfileAPI;

    public RESTClient(Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS).
                        addInterceptor(new AuthIntercepter(context)).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.allergyAPI = retrofit.create(AllergyAPI.class);
        this.mealPlanAPI = retrofit.create(MealPlanAPI.class);
        this.recipeAPI = retrofit.create(RecipeAPI.class);
        this.userProfileAPI = retrofit.create(UserProfileAPI.class);
    }

    public AllergyAPI getAllergyAPI() {
        return allergyAPI;
    }

    public MealPlanAPI getMealPlanAPI() {
        return mealPlanAPI;
    }

    public RecipeAPI getRecipeAPI() {
        return recipeAPI;
    }

    public UserProfileAPI getUserProfileAPI() {
        return userProfileAPI;
    }
}
