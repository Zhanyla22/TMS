package com.example.TMS.repository;

import com.example.TMS.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findTaskByUuid(UUID uuid);
}
