package com.example.TMS.repository;

import com.example.TMS.entity.Comment;
import com.example.TMS.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByUuid(UUID uuid);


    List<Comment> findAllByTaskUuidAndStatus(UUID taskUuid, Status status);
}
