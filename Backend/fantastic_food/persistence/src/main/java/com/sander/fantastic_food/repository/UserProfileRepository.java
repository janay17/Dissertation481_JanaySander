package com.sander.fantastic_food.repository;

import com.sander.fantastic_food.data_model.UserProfileDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileDataModel, Integer> {
    Optional<UserProfileDataModel> findByEmail(String email);
}
