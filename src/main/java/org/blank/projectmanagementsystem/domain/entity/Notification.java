package org.blank.projectmanagementsystem.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "username")
public class Notification implements Serializable {

    @Id
    private String id;

    private String message;

    @Column(nullable = false)
    private LocalDate date;

    private long taskId;

    @JsonIgnore
    private String username;
}
