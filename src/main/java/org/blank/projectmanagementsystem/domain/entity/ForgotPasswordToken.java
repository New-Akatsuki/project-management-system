package org.blank.projectmanagementsystem.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordToken {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable=false)
    private String token;
    @ManyToOne(targetEntity=User.class,fetch= FetchType.EAGER)
    @JoinColumn(nullable=false)
    private User user;
    @Column(nullable=false)
    private LocalDateTime expireTime;
    @Column(nullable=false)
    private boolean isUsed;
}
