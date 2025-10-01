package com.benjamin.taskservice.controller;

import com.benjamin.taskservice.config.JwtService;
import com.benjamin.taskservice.dto.TaskDto;
import com.benjamin.taskservice.response.TaskResponse;
import com.benjamin.taskservice.response.TaskResponses;
import com.benjamin.taskservice.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskDto taskDto,
                                                   @RequestHeader("Authorization") String authHeader){
        String token=authHeader.substring(7);
        UUID userId=jwtService.extractUserId(token);
        TaskResponse taskResponse;

        taskResponse=taskService.createTask(taskDto,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<TaskResponse> getTaskByTitle(@PathVariable String title){
        TaskResponse taskResponse;
            taskResponse=taskService.getTaskByTtitle(title);
            return ResponseEntity.ok(taskResponse);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<TaskResponses> getTaskByUserId(@PathVariable UUID userId,
                                                         @RequestHeader("Authorization") String authHeader){
        String token=authHeader.substring(7);
        UUID id=jwtService.extractUserId(token);
        TaskResponses taskResponses;
        if (id==userId) {
            taskResponses = taskService.getTaskByUserId(userId);
            return ResponseEntity.ok(taskResponses);
        }else
            return ResponseEntity.internalServerError().build();

    }
    @PutMapping("updateTask/{title}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable String title,
                                                   @RequestBody TaskDto taskDto){
        TaskResponse taskResponse;
        taskResponse=taskService.updateTask(title,taskDto);
        return ResponseEntity.ok(taskResponse);
    }

    @DeleteMapping("/deleteTask/{title}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable String title){
        TaskResponse taskResponse;
        taskResponse=taskService.deleteTask(title);
        return ResponseEntity.ok(taskResponse);
    }
}
