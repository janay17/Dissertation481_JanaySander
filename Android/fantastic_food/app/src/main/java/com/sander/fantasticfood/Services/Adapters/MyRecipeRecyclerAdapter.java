package com.sander.fantasticfood.Services.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sander.fantasticfood.Model.Recipe;
import com.sander.fantasticfood.R;
import com.sander.fantasticfood.Services.Logic.RecipeLogic;
import com.sander.fantasticfood.UserInterface.MainActivity;
import com.sander.fantasticfood.UserInterface.ProfileFragments.NewRecipeActivity;
import com.sander.fantasticfood.databinding.FragmentMyRecipeTileBinding;

import java.util.List;

public class MyRecipeRecyclerAdapter extends RecyclerView.Adapter<MyRecipeRecyclerAdapter.MyRecipeViewHolder>{

    private List<Recipe> recipes;
    private FragmentMyRecipeTileBinding binding;
    private Fragment fragment;
    private Context context;

    public MyRecipeRecyclerAdapter(List<Recipe> recipes, Fragment fragment) {
        this.recipes = recipes;
        this.fragment = fragment;

    }

    public MyRecipeRecyclerAdapter(List<Recipe> recipes, Fragment fragment, Context context) {
        this.recipes = recipes;
        this.fragment = fragment;
        this.context = context;

    }

    @NonNull
    @Override
    public MyRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = FragmentMyRecipeTileBinding.inflate(inflater);
        return new MyRecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeViewHolder holder, int position) {

        Glide.with(holder.binding.myRecipeImage)
                .load(recipes.get(position).getRecipeImage())
                .error(Glide.with(holder.binding.myRecipeImage)
                        .load(MainActivity.defaultImage))
                .placeholder(R.mipmap.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.binding.myRecipeImage);

        holder.binding.recipeName.setText(recipes.get(position).getRecipeName());

        holder.binding.editRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(fragment.getActivity(), NewRecipeActivity.class);
            intent.putExtra(NewRecipeActivity.RECIPE, recipes.get(position));
            fragment.startActivity(intent);
        });

        holder.binding.deleteRecipe.setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                .setTitle("Delete Recipe")
                .setMessage("Are you sure you want to delete "+
                        recipes.get(position).getRecipeName() + "?")
                .setIcon(R.drawable.delete)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        new RecipeLogic().deleteRecipe(context, recipes.get(position));
                    }})
                .setNegativeButton(android.R.string.no, null).show();

        });

    }

    @Override
    public int getItemCount() {
        return this.recipes.size();
    }

    public class MyRecipeViewHolder extends RecyclerView.ViewHolder {

        final FragmentMyRecipeTileBinding binding;

        public MyRecipeViewHolder(@NonNull FragmentMyRecipeTileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
