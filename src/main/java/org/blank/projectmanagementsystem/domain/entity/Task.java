package org.blank.projectmanagementsystem.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.Enum.Priority;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column(nullable = false, length = 25)
    private String name;

    @Column(nullable = true, length = 500)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date dueDate;

    @Column(nullable = false)
    private float planHours;

    @Column(nullable = true)
    private Date actualStartDate;

    @Column(nullable = true)
    private Date actualDueDate;

    @Column(nullable = true)
    private float actualHours;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Project project;

    @ManyToMany
    @JoinTable(
            name = "task_assignee",  // Specify the name of the join table
            joinColumns = @JoinColumn(name = "task_id",nullable = false),  // Column in the join table for Project
            inverseJoinColumns = @JoinColumn(name = "assignee_id",nullable = false)  // Column in the join table for Architecture
    )
    private Set<User> assignees;

    @ManyToOne
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask")
    private Set<Task> subtasks;
}
