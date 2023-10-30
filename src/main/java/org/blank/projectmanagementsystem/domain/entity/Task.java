package org.blank.projectmanagementsystem.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.Enum.Priority;
import org.blank.projectmanagementsystem.domain.Enum.TaskGroup;
import org.blank.projectmanagementsystem.domain.Enum.TaskType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;
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
    private Long id;

    @Column(nullable = false, length = 25)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority= Priority.MEDIUM;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private Float planHours;

    @Column(nullable = true)
    private LocalDate actualDueDate;

    @Column(nullable = true)
    private Float actualHours;

    @Column(nullable = false)
    private boolean status = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_group")
    TaskGroup group = TaskGroup.A;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_type", nullable = false)
    private TaskType type = TaskType.TASK;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Project project;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    private Phase phase;

    @ManyToMany
    @JoinTable(
            name = "task_assignee",  // Specify the name of the join table
            joinColumns = @JoinColumn(name = "task_id",nullable = false),  // Column in the join table for Project
            inverseJoinColumns = @JoinColumn(name = "assignee_id",nullable = false)  // Column in the join table for Architecture
    )
    private Set<User> assignees;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Task parentTask;

}
