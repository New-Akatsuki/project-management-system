package org.blank.projectmanagementsystem.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column(nullable = false,unique = true,length = 25)
    private String name;
}
