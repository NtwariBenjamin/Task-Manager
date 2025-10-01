package com.benjamin.taskservice.repo;

import com.benjamin.taskservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
   Optional<Task> findByTitle(String title);

    List<Task> findByUserId(UUID userId);
}
