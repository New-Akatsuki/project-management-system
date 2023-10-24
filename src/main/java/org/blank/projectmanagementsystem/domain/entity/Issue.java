package org.blank.projectmanagementsystem.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Issue implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(nullable = false)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    private boolean solved=false;

    private int impact;

    @Column(length = 500, nullable = false)
    private String direct_cause;

    @Column(length = 500, nullable = false)
    private String root_cause;

    @Column(length = 500, nullable = false)
    private String correctiveAction;

    @Column(length = 500, nullable = false)
    private String preventiveAction;

    @OneToOne
    @JoinColumn(nullable = false)
    private ResponsibleParty responsibleParty;

    @OneToOne
    @JoinColumn(nullable = false)
    private IssuePlace issue_place;

    @OneToMany
    @JoinColumn(nullable = false)
    private Set<IssueCategory> issue_category;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User pic;
}
