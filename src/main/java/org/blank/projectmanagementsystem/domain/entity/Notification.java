package org.blank.projectmanagementsystem.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.blank.projectmanagementsystem.domain.Enum.NotificationType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime date;

    private String link;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private long projectId;

    private long taskId;

    @JsonIgnore
    @ManyToOne
    private User recipient;

    private Boolean isRead;

    public Notification(Boolean isRead) {
        this.isRead = isRead;
    }
}
