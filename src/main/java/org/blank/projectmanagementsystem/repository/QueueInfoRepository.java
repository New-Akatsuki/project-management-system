package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.QueueInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QueueInfoRepository extends JpaRepository<QueueInfo, Long> {

    @Query("SELECT q.routingKey FROM QueueInfo q WHERE q.user.username = :username")
    Optional<String> findRoutingKeyByUsername(String username);

    @Query("SELECT q.queueName FROM QueueInfo q WHERE q.user.username = :username")
    String findQueueNameByUsername(String username);
}
