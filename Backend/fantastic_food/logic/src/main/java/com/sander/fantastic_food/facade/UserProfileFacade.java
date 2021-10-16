package com.sander.fantastic_food.facade;

import com.sander.fantastic_food.UserProfile;
import com.sander.fantastic_food.data_model.RecipeDataModel;
import com.sander.fantastic_food.data_model.UserProfileDataModel;
import com.sander.fantastic_food.mapper.RecipeMapper;
import com.sander.fantastic_food.mapper.UserProfileMapper;
import com.sander.fantastic_food.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserProfileFacade implements CRUD<UserProfile, Integer>{

    private final UserProfileRepository userProfileRepository;
    Logger LOGGER = LoggerFactory.getLogger(UserProfileFacade.class);

    @Autowired
    public UserProfileFacade(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserProfile findById(Integer id) {
        Optional<UserProfileDataModel> dataModel = userProfileRepository.findById(id);
        if(dataModel.isPresent()) {
            return UserProfileMapper.INSTANCE.toProfile(dataModel.get());
        } else {
            LOGGER.warn("User not found");
            return null;
        }
    }

    @Override
    public List<UserProfile> findAll() {
        List<UserProfileDataModel> userProfileDataModels = userProfileRepository.findAll();
        if(!userProfileDataModels.isEmpty()) {
            return UserProfileMapper.INSTANCE.toProfiles(userProfileDataModels);
        } else {
            return null;
        }
    }

    @Override
    public UserProfile saveChanges(UserProfile value) {
        if(value != null) {
            return UserProfileMapper.INSTANCE.toProfile(userProfileRepository.
                    save(UserProfileMapper.INSTANCE.toProfileDataModel(value)));
        } else {
            LOGGER.warn("The user data could not be saved");
            return null;
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        userProfileRepository.deleteById(id);
        if(findById(id) != null) {
            LOGGER.warn("User not deleted");
            return false;
        } else {
            return true;
        }
    }

    public UserProfile findUserByEmailAddress(String email) {
        Optional<UserProfileDataModel> dataModel = this.userProfileRepository.findByEmail(email);
        if (dataModel.isPresent()) {
            return UserProfileMapper.INSTANCE.toProfile(dataModel.get());
        } else {
            return null;
        }
    }
}
