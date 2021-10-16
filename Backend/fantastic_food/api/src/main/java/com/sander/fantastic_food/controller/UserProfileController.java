package com.sander.fantastic_food.controller;

import com.sander.fantastic_food.UserProfile;
import com.sander.fantastic_food.facade.UserProfileFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
public class UserProfileController implements Controller<UserProfile, Integer>{

    private final UserProfileFacade userProfileFacade;

    public UserProfileController(UserProfileFacade userProfileFacade) {
        this.userProfileFacade = userProfileFacade;
    }

    @Override
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<UserProfile> getByID(@PathVariable("id") Integer id) {
        UserProfile profile = this.userProfileFacade.findById(id);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserProfile> getByEmail(@PathVariable("email") String email) {
        UserProfile profile = this.userProfileFacade.findUserByEmailAddress(email);
        return ResponseEntity.ok(profile);
    }

    @Override
    @PostMapping
    public ResponseEntity<UserProfile> saveNew(@RequestBody UserProfile value) {
        String hash = new BCryptPasswordEncoder().encode(value.getPasswordHash());
        value.setPasswordHash(hash);
        UserProfile user = this.userProfileFacade.saveChanges(value);
        return ResponseEntity.ok(user);
    }

    @Override
    @PutMapping
    public ResponseEntity<UserProfile> updateExisting(@RequestBody UserProfile value) {
        UserProfile profile = this.userProfileFacade.saveChanges(value);
        return ResponseEntity.ok(profile);
    }

    @Override
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.userProfileFacade.deleteById(id));
    }

    @PutMapping("/changepassword")
    public ResponseEntity<UserProfile> updatePassword(@RequestBody UserProfile value) {
        String hash = new BCryptPasswordEncoder().encode(value.getPasswordHash());
        value.setPasswordHash(hash);
        UserProfile user = this.userProfileFacade.saveChanges(value);
        return ResponseEntity.ok(user);
    }
}
