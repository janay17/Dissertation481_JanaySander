package com.sander.fantasticfood.Services.API;

import com.sander.fantasticfood.Model.Allergy;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AllergyAPI {
    @GET("/allergies/{id}")
    Observable<Allergy> getAllergyByID(@Path("id") Integer id);

    @POST("/allergies")
    Observable<Allergy> saveNewAllergy(@Body Allergy allergy);

    @PUT("/allergies")
    Observable<Allergy> updateAllergy(@Body Allergy allergy);

    @GET("/allergies")
    Observable<List<Allergy>> getAllAllergies();

    @GET("allergies/search/{search}")
    Observable<List<Allergy>> getBySearch(@Path("search") String searchText);
}
