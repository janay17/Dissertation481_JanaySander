package com.sander.fantasticfood.UserInterface.ProfileFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sander.fantasticfood.Model.MealPlan;
import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.Services.API.MealPlanAPI;
import com.sander.fantasticfood.Services.Adapters.MealPlanRecyclerAdapter;
import com.sander.fantasticfood.Services.RESTClient;
import com.sander.fantasticfood.databinding.FragmentProfileMealPlansBinding;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfileMealPlans extends Fragment {

    private FragmentProfileMealPlansBinding binding;
    private UserProfile user = ProfileMyDetails.currentUser;

    public ProfileMealPlans() {
        // Required empty public constructor
    }


    public static ProfileMealPlans newInstance() {
        ProfileMealPlans fragment = new ProfileMealPlans();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RESTClient restClient = new RESTClient(getActivity());
        MealPlanAPI mealPlanAPI = restClient.getMealPlanAPI();
        Observable<List<MealPlan>> mealPlanObservable = mealPlanAPI.getAllMealPlans();
        mealPlanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealPlans -> {
                    MealPlanRecyclerAdapter adapter = new MealPlanRecyclerAdapter(mealPlans,
                            this.user, this, getActivity());
                    binding.mealplanOptions.setAdapter(adapter);
                    binding.mealplanOptions.setLayoutManager(new LinearLayoutManager(getActivity()));
                }, exception -> {
                    Log.e("ERROR", "populateData: "+"ERROR " + exception.getMessage());
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileMealPlansBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}