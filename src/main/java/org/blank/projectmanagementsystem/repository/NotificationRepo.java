package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {
    List<Notification> findByUsername(String username);
}
