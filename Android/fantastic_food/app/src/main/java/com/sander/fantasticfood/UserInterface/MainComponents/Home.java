package com.sander.fantasticfood.UserInterface.MainComponents;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.Services.Adapters.GalleryRecyclerAdapter;
import com.sander.fantasticfood.Services.AuthIntercepter;
import com.sander.fantasticfood.Services.Logic.UserProfileLogic;
import com.sander.fantasticfood.databinding.FragmentHomeBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private FragmentHomeBinding binding;
    private UserProfile currentUser;

    private final List<String> galleryUrls = new ArrayList<String>() {{
        add("https://www.lifelinescreening.com/-/media/project/life-line-screening/life-line-screening/6-health-education/articles/diet-and-nutrition/fruits-and-vegetables.jpg");
        add("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/gettyimages-1036880806.jpg?crop=0.6666666666666666xw:1xh;center,top&resize=640:*");
        add("https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg?quality=90&resize=700%2C636");
    }};

    public Home() {
    }

    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AuthIntercepter intercepter = new AuthIntercepter(getContext());
        new UserProfileLogic().getUserDataByEmail(getContext(), intercepter.getEmail());


        this.currentUser = UserProfileLogic.getCurrentUser(getActivity());

        if (this.currentUser.getHeight() == null || this.currentUser.getWeight() == null) {
            binding.bmiValue.setText("Unknown");
            binding.bmiStatus.setText("");
        } else {
            double height = Double.parseDouble(this.currentUser.getHeight());
            double weight = Double.parseDouble(this.currentUser.getWeight());
            double bmi = weight / (Math.pow(height, 2));
            DecimalFormat df = new DecimalFormat("#.##");
            binding.bmiValue.setText(df.format(bmi));
            if (bmi < 18.5) binding.bmiStatus.setText("You are underweight");
            if (bmi >= 18.5 && bmi < 25) binding.bmiStatus.setText("You are healthy");
            if (bmi >= 25 && bmi < 30) binding.bmiStatus.setText("You are overweight");
            if (bmi >= 30) binding.bmiStatus.setText("You are obese");
        }

        GalleryRecyclerAdapter adapter = new GalleryRecyclerAdapter(galleryUrls);
        binding.recipeGallery.setAdapter(adapter);
        binding.recipeGallery.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        binding.updateWeightButton.setOnClickListener(v -> {
            Intent intent = new Intent(this.getActivity(), MyProfileActivity.class);
            startActivity(intent);
        });
    }
}