package com.example.TMS.entity.base;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "created_date")
    LocalDateTime createdDate;

    @Column(name = "updated_date")
    LocalDateTime updatedDate;

    /**
     * при создании новой записи
     * берет дату(LocalDateTime) на данный момент и сохраняет на поле createdDate
     */
    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }

    /**
     * при обновлении  записи
     * берет дату(LocalDateTime) на данный момент и сохраняет на поле updatedDate
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
