package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Notification;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {
    //get All Notification by user id
    List<Notification> findAllByRecipientEmailOrRecipientUsername(String email,String username);

}
