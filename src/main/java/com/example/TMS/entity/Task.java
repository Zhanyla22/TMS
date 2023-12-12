package com.example.TMS.entity;

import com.example.TMS.entity.base.BaseEntity;
import com.example.TMS.enums.Priority;
import com.example.TMS.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task extends BaseEntity {

    String title;
    String description;

    @Enumerated(EnumType.STRING)
    Priority priority;

    @ManyToOne
    Users author;

    @ManyToOne
    Users executer;
}
