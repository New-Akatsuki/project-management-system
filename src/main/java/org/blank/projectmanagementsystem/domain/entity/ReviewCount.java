package org.blank.projectmanagementsystem.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class ReviewCount implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private int count;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewerType reviewerType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DevelopmentPhase developmentPhase;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Project project;
}
