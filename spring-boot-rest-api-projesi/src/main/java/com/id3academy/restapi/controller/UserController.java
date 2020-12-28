package com.id3academy.restapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.id3academy.restapi.exception.ResourceNotFoundException;
import com.id3academy.restapi.model.User;
import com.id3academy.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/get-all-users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/get-user-by-id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/get-user-by-first-name/{first_name}")
    public ResponseEntity<User> getUserByFirstName(@PathVariable(value = "first_name") String userFirst_name)
            throws ResourceNotFoundException {
        User user = (User) userRepository.findByFirstName(userFirst_name)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this first name : " + userFirst_name));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/create-user")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
                                                   @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        user.setEmailId(userDetails.getEmailId());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }


    @PutMapping("/update-user-firstName/{first_name}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "first_name") String userFirst_name,
                                           @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = (User) userRepository.findByFirstName(userFirst_name)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this first name : " + userFirst_name));

        user.setEmailId(userDetails.getEmailId());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete-user-by-id/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping("/delete-user-by-first-name/{first_name}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "first_name") String userFirst_name)
            throws ResourceNotFoundException {
        User user = (User) userRepository.findByFirstName(userFirst_name)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this first name :: " + userFirst_name));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
