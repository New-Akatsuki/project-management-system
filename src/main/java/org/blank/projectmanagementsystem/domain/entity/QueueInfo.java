package org.blank.projectmanagementsystem.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(exclude = "user")
public class QueueInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "queue_name")
    private String queueName;

    @Column(name = "routing_key")
    private String routingKey;

    @OneToOne
    private User user;
}
