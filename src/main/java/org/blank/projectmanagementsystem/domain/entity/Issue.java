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

    @Column(length = 700, nullable = false)
    private String content;

//    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

//    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modifyAt;

    private boolean solved=false;

    @Column(length = 700)
    private String impact;

    @Column(length = 700, nullable = false)
    private String directCause;

    @Column(length = 700, nullable = false)
    private String rootCause;

    @Column(length = 700)
    private String correctiveAction;

    @Column(length = 700)
    private String preventiveAction;

    @OneToOne
    @JoinColumn(nullable = false)
    private ResponsibleParty responsibleParty;

    @OneToOne
    @JoinColumn(nullable = false)
    private IssuePlace issuePlace;

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
