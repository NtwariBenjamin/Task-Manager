package com.benjamin.authservice.serviceImpl;

import com.benjamin.authservice.dto.UserDto;
import com.benjamin.authservice.exception.UserNotFoundException;
import com.benjamin.authservice.model.User;
import com.benjamin.authservice.repo.UserRepository;
import com.benjamin.authservice.response.UserResponse;
import com.benjamin.authservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserResponse registerUser(UserDto userDto) {
        Optional<User> existingUser=userRepository.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()){
            throw new IllegalArgumentException("User with: "+userDto.getEmail()+" already exists");
        }

        User user=new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());

        userRepository.save(user);
        return UserResponse.builder()
                .user(user)
                .message("User Registered Successfully!")
                .build();

    }

    @Override
    public UserResponse getUserByFirstName(String firstName) {
        Optional<User> optionalUser=userRepository.findByFirstName(firstName);
        if (optionalUser.isPresent()){
            return UserResponse.builder()
                    .user(optionalUser.get())
                    .message("User "+firstName+" is Found!")
                    .build();
        }else {
            return UserResponse.builder()
                    .user(null)
                    .message("User"+firstName+" is Not Found!")
                    .build();
        }
    }

    @Override
    public UserResponse getUserByLastName(String lastName) {
        Optional<User> optionalUser=userRepository.findByLastName(lastName);
        if (optionalUser.isPresent()){
            return UserResponse.builder()
                    .user(optionalUser.get())
                    .message("User "+lastName+" is Found!")
                    .build();
        }else {
            return UserResponse.builder()
                    .user(null)
                    .message("User"+lastName+" is Not Found!")
                    .build();
        }
    }

    @Override
    public UserResponse updateUser(String firstName, UserDto userDto) {
        Optional<User> optionalUser=userRepository.findByFirstName(firstName);
        if (userDto.getPassword().length()<4){
            return UserResponse.builder()
                    .user(null)
                    .message("Password Should be more than 4 characters")
                    .build();
        }
        User user;
        if (optionalUser.isEmpty()){
            return UserResponse.builder()
                    .user(null)
                    .message("User "+firstName+"is Not Found")
                    .build();
        }
        user=optionalUser.get();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());

        userRepository.save(user);

        return UserResponse.builder()
                .user(user)
                .message("User Updated Successfully!")
                .build();
    }

    @Override
    public UserResponse deleteUser(String firstName) {
        Optional<User> optionalUser=userRepository.findByFirstName(firstName);
        if (optionalUser.isEmpty()){
            return UserResponse.builder()
                    .user(null)
                    .message("User"+firstName+" is Not Found!")
                    .build();
        }
        userRepository.delete(optionalUser.get());
        return  UserResponse.builder()
                .user(null)
                .message("User with this FirstName: "+firstName+" is Deleted Successfully!")
                .build();
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        return UserResponse.builder()
                .user(user)
                .message("User with Email: "+email+" is found!")
                .build();
    }

    @Override
    public UserResponse getUserById(UUID id) {
        User user=userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User with ID: "+id+" is not found"));
        return UserResponse.builder()
                .user(user)
                .message("User with ID:"+id+" is found!")
                .build();
    }
    public UUID getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                .getId();
    }
}
