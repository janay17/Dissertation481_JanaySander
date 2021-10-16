package com.sander.fantasticfood.UserInterface.MainComponents;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sander.fantasticfood.Model.Recipe;
import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.Services.Adapters.RecipeRecyclerAdapter;
import com.sander.fantasticfood.Services.Logic.UserProfileLogic;
import com.sander.fantasticfood.databinding.FragmentFavouriteRecipesBinding;

import java.util.ArrayList;
import java.util.List;

public class FavouriteRecipes extends Fragment {

    private FragmentFavouriteRecipesBinding binding;
    private UserProfile currentUser;

    public FavouriteRecipes() {
    }

    public static FavouriteRecipes newInstance(String param1, String param2) {
        FavouriteRecipes fragment = new FavouriteRecipes();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavouriteRecipesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.currentUser = UserProfileLogic.getCurrentUser(getActivity());

        setUpRecyclerView();

        binding.searchFavouriteText.addTextChangedListener(new TextWatcher() {
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
    }

    private void setUpRecyclerView() {
        String searchText = binding.searchFavouriteText.getText().toString();
        List<Recipe> recipes = new ArrayList<>();
        if (searchText.isEmpty()) {
            recipes = this.currentUser.getUserFavourites();
        } else {
            for (Recipe recipe : this.currentUser.getUserFavourites()) {
                if (recipe.getRecipeName().toLowerCase().contains(searchText.toLowerCase())) {
                    recipes.add(recipe);
                }
            }
        }

        RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(recipes, this, getActivity());
        binding.favouriteRecipes.setAdapter(adapter);
        binding.favouriteRecipes.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    }
}