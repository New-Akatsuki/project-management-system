package org.blank.projectmanagementsystem.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueSolution {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifyAt;

    @Column(nullable = false, columnDefinition = "longtext")
    private String correctiveAction;

    @Column(nullable = false, columnDefinition = "longtext")
    private String preventiveAction;

    @Column(nullable = false, columnDefinition = "longtext")
    private String impact;


}
