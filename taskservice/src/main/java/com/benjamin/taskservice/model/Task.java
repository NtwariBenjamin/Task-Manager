package com.benjamin.taskservice.model;

import com.benjamin.taskservice.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Table
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid",nullable = false)
    private UUID id;

    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    private  UUID userId;
}
