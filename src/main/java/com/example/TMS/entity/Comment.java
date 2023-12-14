package com.example.TMS.entity;

import com.example.TMS.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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

    @Size(max = 300)
    String description;

    @ManyToOne
    Task task;

    @ManyToOne
    User user;
}
