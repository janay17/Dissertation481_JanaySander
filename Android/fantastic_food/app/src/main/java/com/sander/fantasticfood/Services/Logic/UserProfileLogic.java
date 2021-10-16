package com.sander.fantasticfood.Services.Logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.Services.API.UserProfileAPI;
import com.sander.fantasticfood.Services.RESTClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserProfileLogic {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserProfileLogic() {}

    public static UserProfile getCurrentUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FantasticFood"
                , Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("CURRENT_USER", "");
        Gson gson = new Gson();
        return gson.fromJson(userJson, UserProfile.class);
    }

    @SuppressLint("CheckResult")
    public void getUserDataByEmail(Context context, String email) {
        RESTClient restClient = new RESTClient(context);
        UserProfileAPI profileAPI = restClient.getUserProfileAPI();
        Observable<UserProfile> profileObservable = profileAPI.getUserData(email);
        profileObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    sharedPreferences = context.getSharedPreferences("FantasticFood"
                            , Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    editor.putString("CURRENT_USER", json);
                    editor.commit();
                }, exception -> {
                    Log.e("ERROR", "populateData: " + "ERROR " + exception.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void updateUserData(Context context, UserProfile userProfile) {
        RESTClient restClient = new RESTClient(context);
        UserProfileAPI profileAPI = restClient.getUserProfileAPI();
        Observable<UserProfile> profileObservable = profileAPI.updateUser(userProfile);
        profileObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    sharedPreferences = context.getSharedPreferences("FantasticFood"
                            , Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    editor.putString("CURRENT_USER", json);
                    editor.commit();
                }, exception -> {
                    Log.e("ERROR", "populateData: " + "ERROR " + exception.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void updateUserPassword(Context context, UserProfile userProfile) {
        RESTClient restClient = new RESTClient(context);
        UserProfileAPI profileAPI = restClient.getUserProfileAPI();
        Observable<UserProfile> profileObservable = profileAPI.changePassword(userProfile);
        profileObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    sharedPreferences = context.getSharedPreferences("FantasticFood"
                            , Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    editor.putString("CURRENT_USER", json);
                    editor.commit();
                }, exception -> {
                    Log.e("ERROR", "populateData: " + "ERROR " + exception.getMessage());
                });
    }
}
