package com.sander.fantasticfood.Services.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sander.fantasticfood.Model.MealPlan;
import com.sander.fantasticfood.Model.Recipe;
import com.sander.fantasticfood.Services.Logic.RecipeLogic;
import com.sander.fantasticfood.UserInterface.ProfileFragments.NewRecipeActivity;
import com.sander.fantasticfood.databinding.FragmentMealplanTileBinding;

import java.util.List;

public class RecipeMealPlanAdapter extends RecyclerView.Adapter<RecipeMealPlanAdapter.RecipeMealPlanViewHolder>{

    private FragmentMealplanTileBinding binding;
    private List<MealPlan> mealPlans;
    private Context context;
    private Recipe currentRecipe;

    public RecipeMealPlanAdapter(List<MealPlan> mealPlans, Recipe currentRecipe, Context context) {
        this.mealPlans = mealPlans;
        this.currentRecipe = currentRecipe;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeMealPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = FragmentMealplanTileBinding.inflate(inflater);
        return new RecipeMealPlanViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeMealPlanViewHolder holder, int position) {
        holder.binding.mealplanCheckbox.setText(mealPlans.get(position).getMealplanName());

        if(this.currentRecipe != null) {
            if (this.currentRecipe.getRecipeMealPlans().contains(mealPlans.get(position))) {
                holder.binding.mealplanCheckbox.setChecked(true);
            } else {
                holder.binding.mealplanCheckbox.setChecked(false);
            }

            holder.binding.mealplanCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    this.currentRecipe.getRecipeMealPlans().add(mealPlans.get(position));
                } else {
                    this.currentRecipe.getRecipeMealPlans().remove(mealPlans.get(position));
                }
                new RecipeLogic().updateRecipeData(this.context, this.currentRecipe);
            });
        } else {
            holder.binding.mealplanCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    NewRecipeActivity.emptyRecipe.getRecipeMealPlans().add(mealPlans.get(position));
                } else {
                    NewRecipeActivity.emptyRecipe.getRecipeMealPlans().remove(mealPlans.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mealPlans.size();
    }

    public class RecipeMealPlanViewHolder extends RecyclerView.ViewHolder{

        final FragmentMealplanTileBinding binding;


        public RecipeMealPlanViewHolder(@NonNull FragmentMealplanTileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

