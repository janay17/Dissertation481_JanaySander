package com.sander.fantasticfood.Services.Logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.google.android.material.snackbar.Snackbar;
import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.Services.API.UserProfileAPI;
import com.sander.fantasticfood.Services.AuthIntercepter;
import com.sander.fantasticfood.Services.LoginRESTClient;
import com.sander.fantasticfood.UserInterface.MainActivity;
import com.sander.fantasticfood.databinding.FragmentLoginBinding;

import javax.security.auth.login.LoginException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    private FragmentLoginBinding binding;
    private Context context;
    private UserProfile user;

    public LoginViewModel(FragmentLoginBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
    }

    @SuppressLint("CheckResult")
    public void saveToken() throws LoginException {
        try {
            AuthIntercepter authInterceptor = new AuthIntercepter(context);
            authInterceptor.setToken(null);
            LoginRESTClient restClient = new LoginRESTClient(context);
            UserProfileAPI loginApi = restClient.getUserProfileApi();

            Observable<String> loginObservable = loginApi.authenticateUser(this.user);
            loginObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(loginToken -> {
                        if (loginToken == null) {
                            throw new LoginException("Invalid email address or password");
                        }
                        authInterceptor.setToken("Bearer " + loginToken);
                        if (new AuthIntercepter(context).getToken() != null) {
                            authInterceptor.setEmail(user.getEmail());

                            new UserProfileLogic().getUserDataByEmail(context,
                                    user.getEmail());

                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }
                        Log.i("TOKEN", "saveToken: " + authInterceptor.getToken());
                    }, exception -> {
                        Snackbar.make(binding.getRoot(), "Invalid email address or password"
                                , Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
                        }).show();
                    });
            if (new AuthIntercepter(context).getToken() != null) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        } catch (Exception error) {
            throw new LoginException("Invalid email address or password");
        }
    }

    public void loginUser() {

        if (binding.loginEmail.getText().toString().equals("") ||
                binding.loginPassword.getText().toString().equals("")) {
            Snackbar.make(binding.getRoot(), "Valid email address and password are required"
                    , Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
                binding.loginPassword.setText("");
            }).show();
        } else {
            try {
                Editable email = binding.loginEmail.getText();
                Editable password = binding.loginPassword.getText();

                user = new UserProfile(email.toString(), password.toString());
                saveToken();
            } catch (LoginException error) {
                String errorMsg = error.getMessage() == null ? "Something went wrong" : error.getMessage();
                Snackbar.make(binding.getRoot(), errorMsg
                        , Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
                    binding.loginPassword.setText("");
                }).show();
            }
        }

    }
}
