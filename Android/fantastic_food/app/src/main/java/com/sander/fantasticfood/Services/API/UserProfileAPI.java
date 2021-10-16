package com.sander.fantasticfood.Services.API;

import com.sander.fantasticfood.Model.UserProfile;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserProfileAPI {
    @POST("/api/login")
    Observable<String> authenticateUser(@Body UserProfile user);

    @POST("/profiles")
    Observable<UserProfile> registerUser(@Body UserProfile user);

    @GET("/profiles/email/{email}")
    Observable<UserProfile> getUserData(@Path("email") String email);

    @PUT("/profiles")
    Observable<UserProfile> updateUser(@Body UserProfile user);

    @PUT("/profiles/changepassword")
    Observable<UserProfile> changePassword(@Body UserProfile user);
}
