package org.blank.projectmanagementsystem.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User projectManager;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Department department;

    @OneToMany
    @JoinTable(
            name = "contract_member",  // Specify the name of the join table
            joinColumns = @JoinColumn(name = "project_id"),  // Column in the join table for Project
            inverseJoinColumns = @JoinColumn(name = "contract_member_id", nullable = false)  // Column in the join table for Architecture
    )
    private Set<User> contractMembers;

    @OneToMany
    @JoinTable(
            name = "foc_member",  // Specify the name of the join table
            joinColumns = @JoinColumn(name = "project_id"),  // Column in the join table for Project
            inverseJoinColumns = @JoinColumn(name = "foc_member_id")  // Column in the join table for Architecture
    )
    private Set<User> focMembers;


    @ManyToMany
    @JoinTable(
            name = "contract_info",  // Specify the name of the join table
            joinColumns = @JoinColumn(name = "project_id"),  // Column in the join table for Project
            inverseJoinColumns = @JoinColumn(name = "architecture_id",nullable = false)  // Column in the join table for Architecture
    )
    private Set<Architecture> architectures;

    @ManyToMany
    @JoinTable(
            name = "contract_info",  // Specify the name of the join table
            joinColumns = @JoinColumn(name = "project_id"),  // Column in the join table for Project
            inverseJoinColumns = @JoinColumn(name = "system_outline_id",nullable = false)  // Column in the join table
    )
    private Set<SystemOutline> systemOutlines;

    @ManyToMany
    @JoinTable(
            name = "contract_info",  // Specify the name of the join table
            joinColumns = @JoinColumn(name = "project_id"),  // Column in the join table for Project
            inverseJoinColumns = @JoinColumn(name = "deliverable_id",nullable = false)  // Column in the join table
    )
    private Set<Deliverable> deliverables;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Client client;

}
