package com.example.TMS.entity;

import com.example.TMS.entity.base.BaseEntity;
import com.example.TMS.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends BaseEntity{

    String description;

    @ManyToOne
    Task task;

    @ManyToOne
    Users users;
}
