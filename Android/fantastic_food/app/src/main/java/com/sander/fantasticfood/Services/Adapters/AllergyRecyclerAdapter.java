package com.sander.fantasticfood.Services.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sander.fantasticfood.Model.Allergy;
import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.Services.Logic.UserProfileLogic;
import com.sander.fantasticfood.databinding.FragmentAllergyTileBinding;

import java.util.List;

public class AllergyRecyclerAdapter extends RecyclerView.Adapter<AllergyRecyclerAdapter.AllergyViewHolder>{

    private FragmentAllergyTileBinding binding;
    private List<Allergy> allergies;
    private UserProfile currentUser;
    private Fragment fragment;
    private Context context;

    public AllergyRecyclerAdapter(List<Allergy> allergies, UserProfile currentUser,
                                  Fragment fragment, Context context) {
        this.allergies = allergies;
        this.currentUser = currentUser;
        this.fragment = fragment;
        this.context = context;
    }

    @NonNull
    @Override
    public AllergyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = FragmentAllergyTileBinding.inflate(inflater);
        return new AllergyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllergyViewHolder holder, int position) {
        holder.binding.allergyCheckbox.setText(allergies.get(position).getAllergyName());

        if(this.currentUser.getUserAllergies().contains(allergies.get(position))) {
            holder.binding.allergyCheckbox.setChecked(true);
        } else {
            holder.binding.allergyCheckbox.setChecked(false);
        }

        holder.binding.allergyCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                this.currentUser.getUserAllergies().add(allergies.get(position));
            } else {
                this.currentUser.getUserAllergies().remove(allergies.get(position));
            }
            new UserProfileLogic().updateUserData(context, currentUser);
        });
    }

    @Override
    public int getItemCount() {
        return allergies.size();
    }

    public class AllergyViewHolder extends RecyclerView.ViewHolder{

        final FragmentAllergyTileBinding binding;


        public AllergyViewHolder(@NonNull FragmentAllergyTileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
