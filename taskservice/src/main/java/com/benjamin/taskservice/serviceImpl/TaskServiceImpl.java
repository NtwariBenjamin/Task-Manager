package com.benjamin.taskservice.serviceImpl;

import com.benjamin.taskservice.clients.AuthClient;
import com.benjamin.taskservice.dto.TaskDto;
import com.benjamin.taskservice.exception.TaskNotFoundException;
import com.benjamin.taskservice.model.Task;
import com.benjamin.taskservice.repo.TaskRepository;
import com.benjamin.taskservice.response.TaskResponse;
import com.benjamin.taskservice.response.TaskResponses;
import com.benjamin.taskservice.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AuthClient authClient;

    @Override
    public TaskResponse createTask(TaskDto taskDto, UUID userId) {
        Task task=Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .dueDate(taskDto.getDueDate())
                .status(taskDto.getStatus())
                .userId(userId)
                .build();
        return TaskResponse.builder()
                .task(task)
                .message("Task Created Successfully!")
                .build();
    }

    @Override
    public TaskResponse getTaskByTtitle(String title) {
        Task task=taskRepository.findByTitle(title)
                .orElseThrow(()->new TaskNotFoundException("Task with Title: "+title+" Is Not Found"));
        return TaskResponse.builder()
                .task(task)
                .message("Task with Title:"+title+"is found!")
                .build();
    }

    @Override
    public TaskResponses getTaskByUserId(UUID userId) {
        List<Task> task=taskRepository.findByUserId(userId);
        return TaskResponses.builder()
                .task(task)
                .message("Task(s) For User with Id: "+userId+" Found successfully!")
                .build();
    }

    @Override
    public TaskResponse updateTask(String title, TaskDto taskDto) {
        Optional<Task> optionalTask=taskRepository.findByTitle(title);
        if (optionalTask.isEmpty()){
            return TaskResponse.builder()
                    .task(null)
                    .message("Task Not Found!")
                    .build();
        }
        Task task;
        task=optionalTask.get();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        task.setDueDate(taskDto.getDueDate());
        task.setUserId(taskDto.getUserId());

        taskRepository.save(task);

        return TaskResponse.builder()
                .task(task)
                .message("Task Updated Successfully!")
                .build();
    }

    @Override
    public TaskResponse deleteTask(String title) {
        Optional<Task> optionalTask=taskRepository.findByTitle(title);
        if (optionalTask.isEmpty()){
            return TaskResponse.builder()
                    .task(null)
                    .message("Task Not Found!")
                    .build();
        }
        taskRepository.delete(optionalTask.get());
        return TaskResponse.builder()
                .task(null)
                .message("Task Not Found!")
                .build();
    }
}
