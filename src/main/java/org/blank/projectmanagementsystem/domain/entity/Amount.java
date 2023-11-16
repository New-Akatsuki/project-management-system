package org.blank.projectmanagementsystem.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Amount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DevelopmentPhase developmentPhase;

    @Column(nullable = false)
    private int amount;


    @ManyToOne
    @JoinColumn(nullable = false)
    private Project project;


}
