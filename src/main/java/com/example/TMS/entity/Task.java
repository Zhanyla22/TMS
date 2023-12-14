package com.example.TMS.entity;

import com.example.TMS.entity.base.BaseEntity;
import com.example.TMS.enums.Priority;
import com.example.TMS.enums.StatusTask;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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

    @Size(max = 10)
    String title;

    @Size(max = 300)
    String description;

    @Enumerated(EnumType.STRING)
    Priority priority;

    @Enumerated(EnumType.STRING)
    StatusTask statusTask;

    @ManyToOne
    User author;

    @ManyToOne
    User executor;
}
