package com.sander.fantasticfood.Services.Logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;

import com.google.android.material.snackbar.Snackbar;
import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.Services.API.UserProfileAPI;
import com.sander.fantasticfood.Services.LoginRESTClient;
import com.sander.fantasticfood.UserInterface.AuthenticationFragments.SignUpDirections;
import com.sander.fantasticfood.databinding.FragmentSignUpBinding;

import javax.security.auth.login.LoginException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SignUpViewModel {

    private Context context;
    private FragmentSignUpBinding binding;
    private NavController navController;

    public SignUpViewModel(Context context, FragmentSignUpBinding binding,
                           NavController navController) {
        this.context = context;
        this.binding = binding;
        this.navController = navController;
    }

    @SuppressLint("CheckResult")
    public void signUp() {
        try {
            UserProfile user = isValid();
            if (user != null) {
                LoginRESTClient restClient = new LoginRESTClient(context);
                UserProfileAPI loginApi = restClient.getUserProfileApi();

                Observable<UserProfile> registerObservable = loginApi.registerUser(user);
                registerObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(customer -> {
                            if (customer == null) {
                                throw new LoginException("The user sign in failed. Please try" +
                                        " again later");
                            }
                            Snackbar.make(binding.getRoot(), "You have successfully signed up! Please login"
                                    , Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
                            }).show();
                            NavDirections directions = SignUpDirections.actionSignupToLogin();
                            navController.navigate(directions);

                        }, exception -> Snackbar.make(binding.getRoot(), "The user sign in failed. Please try" +
                                        " again later"
                                , Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
                        }).show());
            }
        } catch (LoginException error) {
            Snackbar.make(binding.getRoot(), error.getMessage()
                    , Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
            }).show();
        }
    }

    UserProfile isValid() throws LoginException {
        binding.signupEmailLayout.setError(null);
        binding.signupPasswordLayout.setError(null);
        binding.signupConfirmPasswordLayout.setError(null);
        String email = binding.signupEmail.getText().toString();
        String password = binding.signupPassword.getText().toString();
        String confirm = binding.signupConfirmPassword.getText().toString();

        if (!email.isEmpty() && !password.isEmpty() && !confirm.isEmpty()) {
            if (password.equals(confirm)) {
                if (isValidEmail(email)) {
                    if (password.length() >= 8) {
                        return new UserProfile(email, password);
                    } else {
                        binding.signupPasswordLayout.setError("Password must be at least 8 characters in length.");
                    }
                } else {
                    binding.signupEmailLayout.setError("Invalid email address provided");
                }
            } else {
                binding.signupConfirmPasswordLayout.setError("Passwords do not match.");
            }
        } else {
            throw new LoginException("Please fill in all fields.");
        }
        return null;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
