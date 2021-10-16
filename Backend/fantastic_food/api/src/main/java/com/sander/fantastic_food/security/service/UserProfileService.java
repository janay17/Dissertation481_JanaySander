package com.sander.fantastic_food.security.service;

import com.sander.fantastic_food.UserProfile;
import com.sander.fantastic_food.data_model.UserProfileDataModel;
import com.sander.fantastic_food.facade.UserProfileFacade;
import com.sander.fantastic_food.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService implements UserDetailsService {

    private final UserProfileFacade userProfileFacade;

    @Autowired
    public UserProfileService(UserProfileFacade userProfileFacade) {
        this.userProfileFacade = userProfileFacade;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserProfile profile = this.userProfileFacade.findUserByEmailAddress(username);
        if(profile != null){
            return new UserProfilePrincipal(profile);
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
