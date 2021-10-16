package com.sander.fantasticfood.Services.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sander.fantasticfood.Model.Allergy;
import com.sander.fantasticfood.Model.Recipe;
import com.sander.fantasticfood.Services.Logic.RecipeLogic;
import com.sander.fantasticfood.UserInterface.ProfileFragments.NewRecipeActivity;
import com.sander.fantasticfood.databinding.FragmentAllergyTileBinding;

import java.util.List;

public class RecipeAllergyAdapter extends RecyclerView.Adapter<RecipeAllergyAdapter.RecipeAllergyViewHolder>{

    private FragmentAllergyTileBinding binding;
    private List<Allergy> allergies;
    private Context context;
    private Recipe currentRecipe;

    public RecipeAllergyAdapter(List<Allergy> allergies, Recipe currentRecipe, Context context) {
        this.allergies = allergies;
        this.currentRecipe = currentRecipe;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeAllergyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = FragmentAllergyTileBinding.inflate(inflater);
        return new RecipeAllergyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAllergyViewHolder holder, int position) {
        holder.binding.allergyCheckbox.setText(allergies.get(position).getAllergyName());

        if(this.currentRecipe != null) {
            if(this.currentRecipe.getRecipeAllergies().contains(allergies.get(position))) {
                holder.binding.allergyCheckbox.setChecked(true);
            } else {
                holder.binding.allergyCheckbox.setChecked(false);
            }

            holder.binding.allergyCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked) {
                    this.currentRecipe.getRecipeAllergies().add(allergies.get(position));
                } else {
                    this.currentRecipe.getRecipeAllergies().remove(allergies.get(position));
                }
                new RecipeLogic().updateRecipeData(this.context, this.currentRecipe);
            });
        } else {
            holder.binding.allergyCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked) {
                    NewRecipeActivity.emptyRecipe.getRecipeAllergies().add(allergies.get(position));
                } else {
                    NewRecipeActivity.emptyRecipe.getRecipeAllergies().remove(allergies.get(position));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return allergies.size();
    }

    public class RecipeAllergyViewHolder extends RecyclerView.ViewHolder{

        final FragmentAllergyTileBinding binding;


        public RecipeAllergyViewHolder(@NonNull FragmentAllergyTileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
