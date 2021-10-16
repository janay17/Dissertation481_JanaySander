package com.sander.fantasticfood.UserInterface.ProfileFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sander.fantasticfood.Model.Allergy;
import com.sander.fantasticfood.Model.UserProfile;
import com.sander.fantasticfood.Services.API.AllergyAPI;
import com.sander.fantasticfood.Services.Adapters.AllergyRecyclerAdapter;
import com.sander.fantasticfood.Services.RESTClient;
import com.sander.fantasticfood.databinding.FragmentProfileAllergiesBinding;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfileAllergies extends Fragment {

    private FragmentProfileAllergiesBinding binding;
    private UserProfile user = ProfileMyDetails.currentUser;

    public ProfileAllergies() {
        // Required empty public constructor
    }

    public static ProfileAllergies newInstance() {
        ProfileAllergies fragment = new ProfileAllergies();
        return fragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RESTClient restClient = new RESTClient(getActivity());
        AllergyAPI allergyAPI = restClient.getAllergyAPI();
        Observable<List<Allergy>> allergyObservable = allergyAPI.getAllAllergies();
        allergyObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allergies -> {
                    AllergyRecyclerAdapter adapter = new AllergyRecyclerAdapter(allergies,
                            this.user, this, getActivity());
                    binding.allergyOptions.setAdapter(adapter);
                    binding.allergyOptions.setLayoutManager(new LinearLayoutManager(getActivity()));
                }, exception -> {
                    Log.e("ERROR", "populateData: "+"ERROR " + exception.getMessage());
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileAllergiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}