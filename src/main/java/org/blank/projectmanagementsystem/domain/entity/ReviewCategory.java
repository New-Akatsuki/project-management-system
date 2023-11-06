package org.blank.projectmanagementsystem.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class ReviewCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false, length = 35)
    private String name;

    @OneToMany(mappedBy = "reviewCategory")
    @JsonIgnore
    private List<ReviewCount> reviewCounts;


}
