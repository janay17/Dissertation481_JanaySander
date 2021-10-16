package com.sander.fantasticfood.Services.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sander.fantasticfood.Model.Recipe;
import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.R;
import com.sander.fantasticfood.Services.Logic.UserProfileLogic;
import com.sander.fantasticfood.UserInterface.MainActivity;
import com.sander.fantasticfood.UserInterface.MainComponents.SingleRecipe;
import com.sander.fantasticfood.databinding.FragmentRecipeTileBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeViewHolder>{

    private List<Recipe> recipes;
    private FragmentRecipeTileBinding binding;
    private Fragment fragment;
    private UserProfile currentUser;
    private Context context;

    public RecipeRecyclerAdapter(List<Recipe> recipes, Fragment fragment, Context context) {
        this.recipes = recipes;

        this.currentUser = UserProfileLogic.getCurrentUser(context);

        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = FragmentRecipeTileBinding.inflate(inflater);
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        AtomicBoolean isFavourite = new AtomicBoolean(false);
        Glide.with(holder.binding.recipeImage)
                .load(recipes.get(position).getRecipeImage())
                .error(Glide.with(holder.binding.recipeImage)
                        .load(MainActivity.defaultImage))
                .placeholder(R.mipmap.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.binding.recipeImage);

        holder.binding.recipeName.setText(recipes.get(position).getRecipeName());

        if(this.currentUser.getUserFavourites() != null) {

            List<Integer> favouriteIds = new ArrayList<>();
            for (Recipe favourite: this.currentUser.getUserFavourites()) {
                favouriteIds.add(favourite.getId());
            }

            if(favouriteIds.contains(recipes.get(position).getId())) {
                holder.binding.favouriteIcon.setImageResource(R.drawable.favourite);
                isFavourite.set(true);
            } else {
                holder.binding.favouriteIcon.setImageResource(R.drawable.favourite_outline);
                isFavourite.set(false);
            }
        }

        holder.binding.recipeTile.setOnClickListener(v -> {
            Intent intent = new Intent(fragment.getActivity(), SingleRecipe.class);
            intent.putExtra(SingleRecipe.RECIPE, recipes.get(position));
            fragment.startActivity(intent);
        });

        holder.binding.favouriteIcon.setOnClickListener(v -> {
            if(isFavourite.get()) {
                holder.binding.favouriteIcon.setImageResource(R.drawable.favourite_outline);
                isFavourite.set(false);
                this.currentUser.getUserFavourites().remove(recipes.get(position));
            } else {
                holder.binding.favouriteIcon.setImageResource(R.drawable.favourite);
                isFavourite.set(true);
                this.currentUser.getUserFavourites().add(recipes.get(position));
            }
            new UserProfileLogic().updateUserData(context, this.currentUser);
        });

    }

    @Override
    public int getItemCount() {
        return this.recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        final FragmentRecipeTileBinding binding;

        public RecipeViewHolder(@NonNull FragmentRecipeTileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
