package com.sander.fantasticfood.Services.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sander.fantasticfood.Model.MealPlan;
import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.Services.Logic.UserProfileLogic;
import com.sander.fantasticfood.databinding.FragmentMealplanTileBinding;

import java.util.List;

public class MealPlanRecyclerAdapter extends RecyclerView.Adapter<MealPlanRecyclerAdapter.MealPlanViewHolder>{

    private FragmentMealplanTileBinding binding;
    private List<MealPlan> mealPlans;
    private UserProfile currentUser;
    private Fragment fragment;
    private Context context;

    public MealPlanRecyclerAdapter(List<MealPlan> mealPlans, UserProfile currentUser,
                                  Fragment fragment, Context context) {
        this.mealPlans = mealPlans;
        this.currentUser = currentUser;
        this.fragment = fragment;
        this.context = context;
    }

    @NonNull
    @Override
    public MealPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = FragmentMealplanTileBinding.inflate(inflater);
        return new MealPlanViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlanViewHolder holder, int position) {
        holder.binding.mealplanCheckbox.setText(mealPlans.get(position).getMealplanName());

        if(this.currentUser.getUserMealPlans().contains(mealPlans.get(position))) {
            holder.binding.mealplanCheckbox.setChecked(true);
        } else {
            holder.binding.mealplanCheckbox.setChecked(false);
        }

        holder.binding.mealplanCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                this.currentUser.getUserMealPlans().add(mealPlans.get(position));
            } else {
                this.currentUser.getUserMealPlans().remove(mealPlans.get(position));
            }
            new UserProfileLogic().updateUserData(context, currentUser);
        });
    }

    @Override
    public int getItemCount() {
        return mealPlans.size();
    }

    public class MealPlanViewHolder extends RecyclerView.ViewHolder{

        final FragmentMealplanTileBinding binding;


        public MealPlanViewHolder(@NonNull FragmentMealplanTileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
