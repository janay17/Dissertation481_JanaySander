package com.sander.fantasticfood.UserInterface.ProfileFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.Services.Logic.UserProfileLogic;
import com.sander.fantasticfood.databinding.FragmentProfileMyDetailsBinding;

public class ProfileMyDetails extends Fragment {

    private FragmentProfileMyDetailsBinding binding;
    public static UserProfile currentUser;

    public ProfileMyDetails() {
        // Required empty public constructor
    }

    public static ProfileMyDetails newInstance() {
        ProfileMyDetails fragment = new ProfileMyDetails();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileMyDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentUser = UserProfileLogic.getCurrentUser(getActivity());
        loadUserDetails();

        binding.undoMyDetails.setOnClickListener(v -> {
            loadUserDetails();
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void loadUserDetails() {
        binding.weight.setText(currentUser.getWeight());
        binding.height.setText(currentUser.getHeight());
        binding.profilePassword.setText("");
        binding.profileConfirmPassword.setText("");
    }

    @Override
    public void onPause() {

        String weight = binding.weight.getText().toString();
        String height = binding.height.getText().toString();
        String password = binding.profilePassword.getText().toString();
        String confirm = binding.profileConfirmPassword.getText().toString();
        if(password.isEmpty()) {
            currentUser.setWeight(weight.isEmpty()? null: weight );
            currentUser.setHeight(height.isEmpty()? null: height );
            new UserProfileLogic().updateUserData(getActivity(), currentUser);
        } else {
            if(password.length() >= 8 && password.equals(confirm)) {
                currentUser.setWeight(weight.isEmpty()? null: weight );
                currentUser.setHeight(height.isEmpty()? null: height );
                currentUser.setPasswordHash(password);
                new UserProfileLogic().updateUserPassword(getActivity(), currentUser);
            }
        }
        super.onPause();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}