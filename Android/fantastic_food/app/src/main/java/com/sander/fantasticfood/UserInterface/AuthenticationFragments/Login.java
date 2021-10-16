package com.sander.fantasticfood.UserInterface.AuthenticationFragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.sander.fantasticfood.R;
import com.sander.fantasticfood.Services.Logic.LoginViewModel;
import com.sander.fantasticfood.databinding.FragmentLoginBinding;

public class Login extends Fragment {

    private FragmentLoginBinding binding;
    private NavController navController;
    private LoginViewModel viewModel;

    public Login() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setupViewModel();
        binding.signupButton.setOnClickListener(v -> {
            NavDirections directions = LoginDirections.actionLoginToSignup();
            navController.navigate(directions);
        });
        binding.loginButton.setOnClickListener(v -> {
            viewModel.loginUser();
        });
        binding.forgotPassword.setOnClickListener(v -> {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Forgot Password")
                    .setIcon(R.drawable.password)
                    .setMessage("An email will be sent to change your password.")
                    .setPositiveButton(android.R.string.yes, null).show();
        });
    }

    private void setupViewModel() {
        this.viewModel = new LoginViewModel(binding, getActivity());
    }
}