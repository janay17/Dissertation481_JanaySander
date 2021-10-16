package com.sander.fantasticfood.UserInterface.ProfileFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sander.fantasticfood.Model.Recipe;
import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.Services.Adapters.MyRecipeRecyclerAdapter;
import com.sander.fantasticfood.Services.Logic.UserProfileLogic;
import com.sander.fantasticfood.databinding.FragmentProfileMyRecipesBinding;

import java.util.ArrayList;
import java.util.List;

public class ProfileMyRecipes extends Fragment {

    private FragmentProfileMyRecipesBinding binding;
    private NavController navController;
    private UserProfile currentUser;
    public static MyRecipeRecyclerAdapter adapter;

    public ProfileMyRecipes() {
        // Required empty public constructor
    }

    public static ProfileMyRecipes newInstance() {
        ProfileMyRecipes fragment = new ProfileMyRecipes();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileMyRecipesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpRecyclerView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController =  Navigation.findNavController(view);
        binding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setUpRecyclerView();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.addNewRecipe.setOnClickListener(v -> {
            NavDirections directions = ProfileMyRecipesDirections.myRecipesToAddNew();
            navController.navigate(directions);
        });

        binding.refreshRecipes.setOnClickListener(v -> {
            setUpRecyclerView();
        });
    }

    private void setUpRecyclerView() {
        this.currentUser = UserProfileLogic.getCurrentUser(getActivity());
        String searchText = binding.searchText.getText().toString();
        List<Recipe> recipes = new ArrayList<>();
        if(searchText.isEmpty()){
            recipes = this.currentUser.getUserRecipes();
        } else {
            for(Recipe recipe : this.currentUser.getUserRecipes()) {
                if(recipe.getRecipeName().toLowerCase().contains(searchText.toLowerCase())) {
                    recipes.add(recipe);
                }
            }
        }
        adapter = new MyRecipeRecyclerAdapter(recipes,
                this, getActivity());
        binding.myRecipes.setAdapter(adapter);
        binding.myRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

}