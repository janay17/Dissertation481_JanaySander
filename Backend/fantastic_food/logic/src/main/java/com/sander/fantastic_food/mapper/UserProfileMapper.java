package com.sander.fantastic_food.mapper;

import com.sander.fantastic_food.UserProfile;
import com.sander.fantastic_food.data_model.UserProfileDataModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserProfileMapper {
    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    UserProfile toProfile(UserProfileDataModel dataModel);

    UserProfileDataModel toProfileDataModel(UserProfile profile);

    List<UserProfile> toProfiles(List<UserProfileDataModel> dataModels);
}
