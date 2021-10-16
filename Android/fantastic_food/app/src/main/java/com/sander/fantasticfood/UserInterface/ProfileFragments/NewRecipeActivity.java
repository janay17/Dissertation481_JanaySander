package com.sander.fantasticfood.UserInterface.ProfileFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sander.fantasticfood.Model.Allergy;
import com.sander.fantasticfood.Model.MealPlan;
import com.sander.fantasticfood.Model.Recipe;
import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.R;
import com.sander.fantasticfood.Services.API.AllergyAPI;
import com.sander.fantasticfood.Services.API.MealPlanAPI;
import com.sander.fantasticfood.Services.Adapters.RecipeAllergyAdapter;
import com.sander.fantasticfood.Services.Adapters.RecipeMealPlanAdapter;
import com.sander.fantasticfood.Services.Logic.RecipeLogic;
import com.sander.fantasticfood.Services.Logic.UserProfileLogic;
import com.sander.fantasticfood.Services.RESTClient;
import com.sander.fantasticfood.UserInterface.MainActivity;
import com.sander.fantasticfood.databinding.ActivityNewRecipeBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewRecipeActivity extends AppCompatActivity {

    private ActivityNewRecipeBinding binding;
    private Toolbar toolbar;
    public static final String RECIPE = "com.sander.fantasticfood.UserInterface.ProfileFragments.RECIPE_ID";
    private Recipe currentRecipe;
    public static Recipe emptyRecipe;
    private boolean isNewRecipe;
    private UserProfile currentUser;
    private final List<String> recipeDifficulty = new ArrayList<String>() {{
        add("EASY");
        add("MEDIUM");
        add("HARD");
    }};

    private String newImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.newRecipeToolbar;
        setSupportActionBar(toolbar);


        ArrayAdapter<String> adapterDifficulties = new ArrayAdapter<String>(this,
                R.layout.spinner_item_layout, recipeDifficulty);
        adapterDifficulties.setDropDownViewResource(R.layout.spinner_item_layout);
        binding.newDifficulty.setAdapter(adapterDifficulties);

        Intent intent = getIntent();
        this.currentRecipe = intent.getParcelableExtra(RECIPE);
        loadOriginalData();

        RESTClient restClient = new RESTClient(this);
        AllergyAPI allergyAPI = restClient.getAllergyAPI();
        Observable<List<Allergy>> allergyObservable = allergyAPI.getAllAllergies();
        allergyObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allergies -> {
                    RecipeAllergyAdapter adapter = new RecipeAllergyAdapter(allergies, currentRecipe, this);
                    binding.allergyOptions.setAdapter(adapter);
                    binding.allergyOptions.setLayoutManager(new LinearLayoutManager(this));

                }, exception -> {
                    Log.e("ERROR", "populateData: " + "ERROR " + exception.getMessage());
                });

        MealPlanAPI mealPlanAPI = restClient.getMealPlanAPI();
        Observable<List<MealPlan>> mealPlanObservable = mealPlanAPI.getAllMealPlans();
        mealPlanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealPlans -> {
                    RecipeMealPlanAdapter adapter = new RecipeMealPlanAdapter(mealPlans, currentRecipe, this);
                    binding.mealplanOptions.setAdapter(adapter);
                    binding.mealplanOptions.setLayoutManager(new LinearLayoutManager(this));
                }, exception -> {
                    Log.e("ERROR", "populateData: " + "ERROR " + exception.getMessage());
                });

        this.currentUser = UserProfileLogic.getCurrentUser(this);

        binding.undoRecipeChanges.setOnClickListener(v -> {
            loadOriginalData();
        });

        binding.deleteNewRecipe.setOnClickListener(v -> {
            loadOriginalData();
            this.finish();
        });

        binding.chooseRecipeImage.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Paste the recipe image address");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    newImage = input.getText().toString();
                    Glide.with(binding.newRecipeImage)
                            .load(newImage)
                            .error(Glide.with(binding.newRecipeImage)
                                    .load(MainActivity.defaultImage))
                            .placeholder(R.mipmap.placeholder)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(binding.newRecipeImage);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadOriginalData() {
        if (this.currentRecipe == null) {
            toolbar.setTitle("New Recipe");
            binding.deleteNewRecipe.setVisibility(View.VISIBLE);
            isNewRecipe = true;
            emptyRecipe = new Recipe();
            emptyRecipe.setRecipeAllergies(new ArrayList<>());
            emptyRecipe.setRecipeMealPlans(new ArrayList<>());
            newImage = "";
            Glide.with(binding.newRecipeImage)
                    .load(newImage)
                    .error(Glide.with(binding.newRecipeImage)
                            .load(MainActivity.defaultImage))
                    .placeholder(R.mipmap.placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.newRecipeImage);
            binding.newRecipeName.setText("");
            binding.newTime.setText("");
            binding.newRecipeInstructions.setText("");
        } else {
            toolbar.setTitle("Edit Recipe");
            isNewRecipe = false;
            binding.deleteNewRecipe.setVisibility(View.GONE);
            Glide.with(binding.newRecipeImage)
                    .load(currentRecipe.getRecipeImage())
                    .error(Glide.with(binding.newRecipeImage)
                            .load(MainActivity.defaultImage))
                    .placeholder(R.mipmap.placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.newRecipeImage);
            binding.newRecipeName.setText(currentRecipe.getRecipeName());
            binding.newDifficulty.setSelection(recipeDifficulty.indexOf(currentRecipe.getDifficulty().trim()));
            binding.newTime.setText(Integer.toString(currentRecipe.getPreparationTime()));
            binding.newRecipeInstructions.setText(currentRecipe.getInstructions());
            newImage = currentRecipe.getRecipeImage();
        }
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onPause() {

        if (!isNewRecipe) {
            if (binding.newRecipeName.getText().toString().isEmpty()) {
//                Snackbar.make(binding.getRoot(), "Please provide a recipe name."
//                        , Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
//                }).show();
            } else {
                if (binding.newRecipeInstructions.getText().toString().isEmpty()) {
//                    Snackbar.make(binding.getRoot(), "Please provide recipe instructions."
//                            , Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
//                    }).show();
                } else {
                    int time = 0;
                    if (!binding.newTime.getText().toString().isEmpty()) {
                        time = Integer.parseInt(binding.newTime.getText().toString());
                    }
                    currentRecipe.setRecipeImage(newImage);
                    currentRecipe.setDifficulty(recipeDifficulty.get(binding.newDifficulty.getSelectedItemPosition()));
                    currentRecipe.setRecipeName(binding.newRecipeName.getText().toString());
                    currentRecipe.setInstructions(binding.newRecipeInstructions.getText().toString());
                    currentRecipe.setPreparationTime(time);
                    new RecipeLogic().updateRecipeData(this, this.currentRecipe);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if (binding.newRecipeName.getText().toString().isEmpty()) {
//                Snackbar.make(binding.getRoot(), "Please provide a recipe name."
//                        , Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
//                }).show();
            } else {
                if (binding.newRecipeInstructions.getText().toString().isEmpty()) {
//                    Snackbar.make(binding.getRoot(), "Please provide recipe instructions."
//                            , Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
//                    }).show();
                } else {
                    int time = 0;
                    if (!binding.newTime.getText().toString().isEmpty()) {
                        time = Integer.parseInt(binding.newTime.getText().toString());
                    }
                    emptyRecipe.setRecipeImage(newImage);
                    emptyRecipe.setDifficulty(recipeDifficulty.get(binding.newDifficulty.getSelectedItemPosition()));
                    emptyRecipe.setRecipeName(binding.newRecipeName.getText().toString());
                    emptyRecipe.setInstructions(binding.newRecipeInstructions.getText().toString());
                    emptyRecipe.setPreparationTime(time);
                    new RecipeLogic().saveNewRecipe(this, emptyRecipe);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        super.onPause();
    }
}