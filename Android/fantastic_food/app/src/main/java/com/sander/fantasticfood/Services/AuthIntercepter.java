package com.sander.fantasticfood.Services;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthIntercepter implements Interceptor {
    private Context context;

    public AuthIntercepter(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        requestBuilder.addHeader("Authorization", getToken());
        return chain.proceed(requestBuilder.build());
    }

    public void setToken(String loginToken) {
        SharedPreferences token_preferences =
                context.getSharedPreferences("TOKEN_PREFERENCES",
                        Context.MODE_PRIVATE);
        token_preferences.edit().
                putString("SHARED_PREFERENCE_TOKEN", loginToken).apply();
    }

    public String getToken() {
        SharedPreferences token_preferences = context.getSharedPreferences("TOKEN_PREFERENCES",
                Context.MODE_PRIVATE);
        String token = token_preferences.getString("SHARED_PREFERENCE_TOKEN", null);
        return token;
    }

    public void setEmail(String email) {
        SharedPreferences token_preferences =
                context.getSharedPreferences("TOKEN_PREFERENCES",
                        Context.MODE_PRIVATE);
        token_preferences.edit().
                putString("SHARED_PREFERENCE_EMAIL", email).apply();
    }

    public String getEmail() {
        SharedPreferences token_preferences = context.getSharedPreferences("TOKEN_PREFERENCES",
                Context.MODE_PRIVATE);
        String email = token_preferences.getString("SHARED_PREFERENCE_EMAIL", null);
        return email;
    }
}
