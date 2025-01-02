package com.AAAW.MovieAPP.Controllers;

import com.AAAW.MovieAPP.Repositories.MediaRepo;
import com.AAAW.MovieAPP.Repositories.UserRepo;
import com.AAAW.MovieAPP.models.Media;
import com.AAAW.MovieAPP.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.AAAW.MovieAPP.Exceptions.UserNotFoundException;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepo userRepo;
    private final MediaRepo mediaRepo;

    public UserController(UserRepo userRepo, MediaRepo mediaRepo) {
        this.userRepo = userRepo;
        this.mediaRepo = mediaRepo;
    }

    @GetMapping("/")
    List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // Add new User
    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody User newUser) {
        if (userRepo.findByEmail(newUser.getEmail().toLowerCase()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");

        try {
            userRepo.save(newUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user. Reason: " + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    private record Creds(
            String email,
            String password
    ) {
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Creds creds) {
        User user = userRepo.findByEmail(creds.email.toLowerCase())
                .orElseThrow(() -> new UserNotFoundException("No user found with email: " + creds.email));

        if (creds.password.equals(user.getPassword()))
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(user.getId());
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
    }

    // get favorite media using user id
    @GetMapping("/{user_id}/favorites")
    public List<Media> getFavorites(@PathVariable String user_id) {
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return user.getLikedMediaIds();
    }

    @GetMapping("/{user_id}")
    public User getUser(@PathVariable String user_id) {
        return userRepo.findById(user_id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    // Add a favorite media to a user
    @PostMapping("/{user_id}/favorites/{media_id}")
    public ResponseEntity<String> addFavorite(@PathVariable String user_id, @PathVariable String media_id) {
        // Fetch the User and Media documents
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Media media = mediaRepo.findById(media_id)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        // Add the media reference to the user's likedMediaIds list
        user.getLikedMediaIds().add(media);  // Add the Media reference to the list

        // Save the updated user document
        userRepo.save(user);

        return ResponseEntity.status(HttpStatus.OK).body("Favorite media added successfully");
    }

}
