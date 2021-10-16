package com.sander.fantasticfood.UserInterface.AuthenticationFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.Services.Logic.LoginViewModel;
import com.sander.fantasticfood.Services.Logic.SignUpViewModel;
import com.sander.fantasticfood.Services.LoginRESTClient;
import com.sander.fantasticfood.Services.API.UserProfileAPI;
import com.sander.fantasticfood.databinding.FragmentSignUpBinding;

import javax.security.auth.login.LoginException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SignUp extends Fragment {

    private FragmentSignUpBinding binding;
    private NavController navController;
    private SignUpViewModel viewModel;

    public SignUp() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setupViewModel();
        binding.signupBackButton.setOnClickListener(v -> {
            NavDirections directions = SignUpDirections.actionSignupToLogin();
            navController.navigate(directions);
        });
        binding.signupConfirmButton.setOnClickListener(v -> viewModel.signUp());
    }

    private void setupViewModel() {
        this.viewModel = new SignUpViewModel(getContext(),binding, navController);
    }

}