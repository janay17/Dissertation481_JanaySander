package com.sander.fantasticfood.UserInterface.MainComponents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sander.fantasticfood.Model.Allergy;
import com.sander.fantasticfood.Model.MealPlan;
import com.sander.fantasticfood.Model.Recipe;
import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.R;
import com.sander.fantasticfood.Services.Logic.UserProfileLogic;
import com.sander.fantasticfood.UserInterface.MainActivity;
import com.sander.fantasticfood.databinding.ActivitySingleRecipeBinding;


public class SingleRecipe extends AppCompatActivity {

    private ActivitySingleRecipeBinding binding;
    Toolbar toolbar;
    public static final String RECIPE = "com.sander.fantasticfood.UserInterface.MainComponents.RECIPE_ID";
    private Recipe currentRecipe;
    private UserProfile currentUser;
    private boolean isFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingleRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = binding.recipeToolbar;
        setSupportActionBar(toolbar);

        this.currentUser = UserProfileLogic.getCurrentUser(this);

        Intent intent = getIntent();
        this.currentRecipe = intent.getParcelableExtra(RECIPE);
        Glide.with(binding.singleRecipeImage)
                .load(currentRecipe.getRecipeImage())
                .error(Glide.with(binding.singleRecipeImage)
                        .load(MainActivity.defaultImage))
                .placeholder(R.mipmap.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.singleRecipeImage);
        binding.singleRecipeName.setText(currentRecipe.getRecipeName());
        binding.singleRecipePreptime.setText("Preparation: " + currentRecipe.getPreparationTime() + " mins");

        StringBuilder allergens = new StringBuilder("");
        for (Allergy allergy : currentRecipe.getRecipeAllergies()) {
            allergens.append(allergy + "\n");
        }
        binding.containingAllergens.setText(allergens);

        isFavourite = currentUser.getUserFavourites().contains(currentRecipe);
        StringBuilder mealPlans = new StringBuilder("");
        for (MealPlan mealPlan : currentRecipe.getRecipeMealPlans()) {
            mealPlans.append(mealPlan + "\n");
        }
        binding.suitableMealplans.setText(mealPlans);

        binding.instructionText.setText(currentRecipe.getInstructions());

        binding.singleRecipeDifficulty.setText(currentRecipe.getDifficulty());

        if (currentRecipe.getDifficulty().trim().equals("EASY")) {
            binding.star1.setVisibility(View.VISIBLE);
            binding.star2.setVisibility(View.GONE);
            binding.star3.setVisibility(View.GONE);
        }
        if (currentRecipe.getDifficulty().trim().equals("MEDIUM")) {
            binding.star1.setVisibility(View.VISIBLE);
            binding.star2.setVisibility(View.VISIBLE);
            binding.star3.setVisibility(View.GONE);
        }
        if (currentRecipe.getDifficulty().trim().equals("HARD")) {
            binding.star1.setVisibility(View.VISIBLE);
            binding.star2.setVisibility(View.VISIBLE);
            binding.star3.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_toolbar_menu, menu);
        MenuItem favouriteMenu = menu.findItem(R.id.action_favourite);
        if (this.currentUser.getUserFavourites().contains(currentRecipe)) {
            favouriteMenu.setIcon(R.drawable.favourite_red);
            isFavourite = true;
        } else {
            favouriteMenu.setIcon(R.drawable.favourite_outline);
            isFavourite = false;
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favourite:
                if (isFavourite) {
                    item.setIcon(R.drawable.favourite_outline);
                    isFavourite = false;
                    this.currentUser.getUserFavourites().remove(currentRecipe);
                } else {
                    item.setIcon(R.drawable.favourite_red);
                    isFavourite = true;
                    this.currentUser.getUserFavourites().add(currentRecipe);
                }
                new UserProfileLogic().updateUserData(this, this.currentUser);
                return true;
            case R.id.action_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, currentRecipe.getRecipeName() + "\n"
                        + "Instructions:\n" + currentRecipe.getInstructions());
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Fantastic Food: "
                        + currentRecipe.getRecipeName());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}