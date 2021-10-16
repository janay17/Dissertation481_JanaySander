package com.sander.fantasticfood.UserInterface.MainComponents;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.sander.fantasticfood.Services.Logic.RecipeLogic;
import com.sander.fantasticfood.Services.Logic.RecipeViewModel;
import com.sander.fantasticfood.databinding.FragmentExploreRecipesBinding;

public class ExploreRecipes extends Fragment {

    private FragmentExploreRecipesBinding binding;
    private RecipeViewModel recipeViewModel;

    public ExploreRecipes() {}

    public static ExploreRecipes newInstance() {
        ExploreRecipes fragment = new ExploreRecipes();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExploreRecipesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadSearchResults();
        binding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadSearchResults();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.nextButton.setOnClickListener(v -> {
            recipeViewModel.setCurrentPage(recipeViewModel.getCurrentPage() + 1);
            loadSearchResults();
        });

        binding.previousButton.setOnClickListener(v -> {
            recipeViewModel.setCurrentPage(recipeViewModel.getCurrentPage() - 1);
            loadSearchResults();
        });

        binding.basedOnPreferences.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loadSearchResults();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    public void loadSearchResults() {
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        binding.pageNumber.setText("Page " + (recipeViewModel.getCurrentPage() + 1));
        RecipeLogic recipeLogic = new RecipeLogic(this,
                binding.basedOnPreferences.isChecked(), getActivity());
        String searchText = binding.searchText.getText().toString();
        if (searchText.isEmpty()) {
            searchText = "*";
        }
        recipeLogic.searchRecipes(this.getContext(), searchText, binding.allRecipes);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSearchResults();
    }
}