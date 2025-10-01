package com.benjamin.taskservice.response;

import com.benjamin.taskservice.model.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponses {
    private List<Task> task;
    private String message;
}
