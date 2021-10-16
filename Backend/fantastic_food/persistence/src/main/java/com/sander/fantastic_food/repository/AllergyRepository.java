package com.sander.fantastic_food.repository;

import com.sander.fantastic_food.data_model.AllergyDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllergyRepository extends JpaRepository<AllergyDataModel, Integer> {

    List<AllergyDataModel> findByAllergyNameContaining(String allergySearch);
}
