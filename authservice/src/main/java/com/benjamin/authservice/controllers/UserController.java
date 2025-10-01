package com.benjamin.authservice.controllers;

import com.benjamin.authservice.config.JwtService;
import com.benjamin.authservice.dto.LoginRequest;
import com.benjamin.authservice.dto.UserDto;
import com.benjamin.authservice.exception.UserNotFoundException;
import com.benjamin.authservice.model.User;
import com.benjamin.authservice.response.LoginResponse;
import com.benjamin.authservice.response.UserResponse;
import com.benjamin.authservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserDto userDto){
        UserResponse userResponse;
        try {
            log.info("Registering User with user with Request; {}",userDto);
            userResponse=userService.registerUser(userDto);
            return ResponseEntity.ok(userResponse);
        }catch (Exception e){
            log.error("Error Registering User: {}", userDto, e);
            throw new RuntimeException("Error Creating new User");
        }
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        try {
            UserResponse userResponse = userService.getUserByEmail(email);
            return ResponseEntity.ok(userResponse);
        } catch (RuntimeException e) {
            log.error("Error retrieving User with Email: {}", email, e);
            throw new RuntimeException("Error retrieving User");
        } catch (Exception e) {
            log.warn("User {} is not found", email, e);
            throw new UserNotFoundException("User " + email + " is not Found");
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        try {
            UserResponse userResponse = userService.getUserById(id);
            return ResponseEntity.ok(userResponse);
        } catch (RuntimeException e) {
            log.error("Error retrieving User with Email: {}", id, e);
            throw new RuntimeException("Error retrieving User");
        } catch (Exception e) {
            log.warn("User {} is not found", id, e);
            throw new UserNotFoundException("User " + id + " is not Found");
        }
    }


    @GetMapping("/{firstName}")
    public  ResponseEntity<UserResponse> getUserByFirstName(@PathVariable String firstName){
        UserResponse userResponse;
        try {
            userResponse=userService.getUserByFirstName(firstName);
            return ResponseEntity.ok(userResponse);
        }catch (RuntimeException e) {
            log.error("Error retrieving User with FirstName: {}", firstName, e);
            throw new RuntimeException("Error retrieving User");
        } catch (Exception e) {
            log.warn("User {} is not found", firstName, e);
            throw new UserNotFoundException("User " + firstName + "is not Found");
        }
    }

    @GetMapping("/lastname/{lastName}")
    public  ResponseEntity<UserResponse> getUserByLastName(@PathVariable String lastName){
        UserResponse userResponse;
        try {
            userResponse=userService.getUserByLastName(lastName);
            return ResponseEntity.ok(userResponse);
        }catch (RuntimeException e) {
            log.error("Error retrieving User with LastName: {}", lastName, e);
            throw new RuntimeException("Error retrieving User");
        } catch (Exception e) {
            log.warn("User {} is not found", lastName, e);
            throw new UserNotFoundException("User " + lastName + "is not Found");
        }
    }

    @PutMapping("updateUser/{firstName}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String firstName,@RequestBody UserDto userDto){
        UserResponse userResponse;
        try {
            log.info("Updating User with Request: {}", userDto);
            userResponse=userService.updateUser(firstName,userDto);
            return ResponseEntity.ok(userResponse);
        }catch (RuntimeException e) {
            log.error("Error Updating User with FirstName: {}", firstName, e);
            throw new RuntimeException("Error Updating User", e);
        } catch (Exception e) {
            log.warn("User {} is not found", firstName, e);
            throw new UserNotFoundException("User Not Found");
        }
    }

    @DeleteMapping("/deleteUser/{firstName}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String firstName){
        UserResponse userResponse;
        try {
            log.info("Deleting User By firstName: {}", firstName);
            userResponse = userService.deleteUser(firstName);
            return ResponseEntity.ok(userResponse);
        }catch (RuntimeException e) {
            log.error("Error Deleting User: {}", firstName, e);
            throw new RuntimeException("Error Deleting User: " + firstName, e);
        } catch (Exception e) {
            log.warn("User with msisdn: {} not found", firstName, e);
            throw new UserNotFoundException("User: " + firstName + " not found");
        }
    }

    @GetMapping("/id")
    public ResponseEntity<UUID> getUserIdByEmail(@RequestParam String email) {
        UUID userId = userService.getUserIdByEmail(email);
        return ResponseEntity.ok(userId);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = null;
        String token;

        Authentication authentication=authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),loginRequest.getPassword()
                ));
        if (authentication.isAuthenticated()){
            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
            UUID userId = userService.getUserIdByEmail(userDetails.getUsername());
            token=jwtService.generateToken(userDetails,userId);
            loginResponse=LoginResponse.builder()
                    .token(token)
                    .message("User Logged in Successfully!")
                    .build();
            return ResponseEntity.ok(loginResponse);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

}
