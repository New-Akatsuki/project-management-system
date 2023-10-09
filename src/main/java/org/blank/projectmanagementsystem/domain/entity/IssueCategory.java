package org.blank.projectmanagementsystem.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class IssueCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column(unique = true,nullable = false, length = 50)
    private String name;
}
