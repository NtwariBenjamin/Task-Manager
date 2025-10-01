package com.benjamin.authservice.services;

import com.benjamin.authservice.dto.UserDto;
import com.benjamin.authservice.response.UserResponse;

import java.util.UUID;

public interface UserService {
    UserResponse registerUser(UserDto userDto);


    UserResponse getUserByFirstName(String firstName);

    UserResponse getUserByLastName(String lastName);

    UserResponse updateUser(String firstName, UserDto userDto);

    UserResponse deleteUser(String firstName);

    UserResponse getUserByEmail(String email);

    UserResponse getUserById(UUID id);

    UUID getUserIdByEmail(String username);
}
