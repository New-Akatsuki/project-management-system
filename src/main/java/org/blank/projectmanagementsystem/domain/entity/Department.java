package org.blank.projectmanagementsystem.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column(unique = true,nullable = false,length = 50)
    private String name;

    private boolean active=true;

}
