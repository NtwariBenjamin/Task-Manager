package com.benjamin.taskservice.service;


import com.benjamin.taskservice.dto.TaskDto;
import com.benjamin.taskservice.response.TaskResponse;
import com.benjamin.taskservice.response.TaskResponses;

import java.util.UUID;

public interface TaskService {
    TaskResponse createTask(TaskDto taskDto, UUID userId);

    TaskResponse getTaskByTtitle(String title);

    TaskResponses getTaskByUserId(UUID userId);

    TaskResponse updateTask(String title, TaskDto taskDto);

    TaskResponse deleteTask(String title);
}
