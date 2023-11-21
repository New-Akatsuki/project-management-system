package org.blank.projectmanagementsystem.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.blank.projectmanagementsystem.domain.Enum.ProjectStatus;
import org.hibernate.annotations.OnDelete;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"projectManager","contractMembers","focMembers","architectures","systemOutlines","deliverables","client"})
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(nullable = false,length = 500)
    private String background;

    @Column(nullable = false,length = 500)
    private String objective;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User projectManager;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Department department;

    @ManyToMany
    @JoinTable(
            name = "contract_member",  // Specify the name of the join table
            joinColumns = @JoinColumn(name = "project_id"),  // Column in the join table for Project
            inverseJoinColumns = @JoinColumn(name = "contract_member_id", nullable = false)  // Column in the join table for Architecture
    )
    private Set<User> contractMembers;

    @ManyToMany
    @JoinTable(
            name = "foc_member",  // Specify the name of the join table
            joinColumns = @JoinColumn(name = "project_id"),  // Column in the join table for Project
            inverseJoinColumns = @JoinColumn(name = "foc_member_id")  // Column in the join table for Architecture
    )
    private Set<User> focMembers;


    @ManyToMany
    private Set<Architecture> architectures = new HashSet<>();

    @ManyToMany
    private Set<SystemOutline> systemOutlines = new HashSet<>();

    @ManyToMany
    private Set<Deliverable> deliverables = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private List<Amount> amounts;

    @OneToMany(mappedBy = "project")
    private List<ReviewCount> reviewCounts;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Client client;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectStatus status = ProjectStatus.ONGOING;
}
