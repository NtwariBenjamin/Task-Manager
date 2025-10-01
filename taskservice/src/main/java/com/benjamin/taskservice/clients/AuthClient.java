package com.benjamin.taskservice.clients;

import com.benjamin.taskservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "auth-service", url = "http://localhost:8080")
public interface AuthClient {

    @GetMapping("/auth/email/{email}")
    UserDto getUserByEmail(@PathVariable String email);

    @GetMapping("/auth/id/{id}")
    UserDto getUserById(@PathVariable UUID id);
}
