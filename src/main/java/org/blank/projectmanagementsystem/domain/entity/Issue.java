package org.blank.projectmanagementsystem.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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

    @Column(length = 700, nullable = false)
    private String content;

    @Column(nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(nullable = false)
    private OffsetDateTime modifyAt = OffsetDateTime.now();

    private boolean solved=false;

    @Column(nullable = false, length = 700)
    private String impact;

    @Column(length = 700, nullable = false)
    private String direct_cause;

    @Column(length = 700, nullable = false)
    private String root_cause;

    @Column(length = 700, nullable = false)
    private String correctiveAction;

    @Column(length = 700, nullable = false)
    private String preventiveAction;

    @OneToOne
    @JoinColumn(nullable = false)
    private ResponsibleParty responsibleParty;

    @OneToOne
    @JoinColumn(nullable = false)
    private IssuePlace issue_place;

    @OneToOne
    @JoinColumn(nullable = false)
    private IssueCategory issueCategory;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User pic;
}
