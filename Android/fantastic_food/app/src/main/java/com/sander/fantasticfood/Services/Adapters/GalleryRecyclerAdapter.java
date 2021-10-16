package com.sander.fantasticfood.Services.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sander.fantasticfood.R;
import com.sander.fantasticfood.UserInterface.MainActivity;
import com.sander.fantasticfood.databinding.FragmentAllergyTileBinding;
import com.sander.fantasticfood.databinding.GalleryTileBinding;

import java.util.List;

public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryRecyclerAdapter.GalleryViewHolder>{

    private GalleryTileBinding binding;
    private List<String> galleryUrls;

    public GalleryRecyclerAdapter(List<String> galleryUrls) {
        this.galleryUrls = galleryUrls;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = GalleryTileBinding.inflate(inflater);
        return new GalleryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        Glide.with(holder.binding.galleryImage)
                .load(galleryUrls.get(position))
                .error(Glide.with(holder.binding.galleryImage)
                        .load(MainActivity.defaultImage))
                .placeholder(R.mipmap.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.binding.galleryImage);
    }

    @Override
    public int getItemCount() {
        return galleryUrls.size();
    }


    public class GalleryViewHolder extends RecyclerView.ViewHolder{

        final GalleryTileBinding binding;


        public GalleryViewHolder(@NonNull GalleryTileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}


