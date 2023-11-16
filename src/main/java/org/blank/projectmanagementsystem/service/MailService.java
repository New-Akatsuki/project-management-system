package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.User;

public interface MailService {
    void sendDefaultPassword(User user, String defaultPassword);
}
