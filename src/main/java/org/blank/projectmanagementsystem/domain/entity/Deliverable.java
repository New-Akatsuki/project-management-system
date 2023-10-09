package org.blank.projectmanagementsystem.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Deliverable implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column(unique = true,nullable = false, length =45)
    private String name;
}
