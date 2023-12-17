package com.example.TMS.repository;

import com.example.TMS.entity.Task;
import com.example.TMS.entity.User;
import com.example.TMS.enums.Status;
import com.example.TMS.enums.StatusTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;


public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findTaskByUuid(UUID uuid);

    Page<Task> findAllByAuthorAndStatus(User author, Status status, Pageable pageable);

    Page<Task> findAllByExecutorAndStatus(User executor, Status status, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE " +
            "(t.status = :status AND t.statusTask = :statusTask) " +
            "OR (t.status = :status AND t.author.id = :authorId)" +
            "OR (t.status = :status AND t.executor.id = :executorId)" +
            "order by t.createdDate DESC ")
    Page<Task> findAllByStatusAndStatusTaskAuthorIdOrExecutorId(Status status, StatusTask statusTask, Long authorId, Long executorId, Pageable pageable);

    Page<Task> findAllByOrderByCreatedDateDesc(Pageable pageable);
}
