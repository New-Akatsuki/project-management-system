package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.Role;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public void changeDefaultPassword(String password) {
        //get current username from security context
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //get user from database
        User user = userRepository.findByUsername(username).orElse(null);
        //change password
        if(user != null) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
    }
    @Override
    public void changePassword(String currentPassword,String newPassword) {
        //get current username from security context
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //get user from database
        User user = userRepository.findByUsername(username).orElse(null);

    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

//    @Override
//    public Optional<User> getUserById(Long userId) {
//        return userRepository.findByUserId(userId);
//    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }



}
