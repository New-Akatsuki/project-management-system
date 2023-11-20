package org.blank.projectmanagementsystem.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @Column(nullable = false, columnDefinition = "longtext")
    private String content;

//    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

//    @Column(nullable = false)
    @Column(nullable = true)
    private LocalDateTime modifyAt;

    private boolean solved=false;

    @Column(columnDefinition = "longtext")
    private String impact;

    @Column(nullable = false, columnDefinition = "longtext")
    private String directCause;

    @Column(nullable = false, columnDefinition = "longtext")
    private String rootCause;

    @Column(columnDefinition = "longtext")
    private String correctiveAction;

    @Column(columnDefinition = "longtext")
    private String preventiveAction;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ResponsibleParty responsibleParty;

    @ManyToOne
    @JoinColumn(nullable = false)
    private IssuePlace issuePlace;

    @ManyToOne
    @JoinColumn(nullable = false)
    private IssueCategory issueCategory;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User pic;

    @Column(nullable = true)
    private LocalDateTime solutionCreatedAt;

    @Column(nullable = true)
    private LocalDateTime solutionModifiedAt;
}
