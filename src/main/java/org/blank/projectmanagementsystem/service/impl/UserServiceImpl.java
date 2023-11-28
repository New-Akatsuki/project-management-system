package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ChangePasswordFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ProfileEditFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static java.lang.Math.toIntExact;

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
        //create 8 number random password for user
        if(user.getPassword() == null) {
            String password = String.valueOf((int) (Math.random() * 100000000));
            user.setPassword(passwordEncoder.encode(password));
        }
        return userRepository.save(user);
    }

    @Override
    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public ChangePasswordFormInput changePassword(String currentPassword, String newPassword) {
        //get current username from security context
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //get user from database

        User user = userRepository.findByUsernameOrEmail(username, username).orElse(null);
        log.info("User found: {}", user);
        //change password
        if (user != null) {
            if (passwordEncoder.matches(currentPassword, user.getPassword())) {
                log.info("Password matches {}\n\n\n", newPassword);
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setDefaultPassword(false);
                userRepository.save(user);
            }
            return new ChangePasswordFormInput();
        }
        return new ChangePasswordFormInput();
    }

    @Override
    public void updatePassword(String newPassword) {
        //get user from database
        User user = userRepository.findByUsernameOrEmail(getUsername(), getUsername()).orElse(null);
        //change password
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setDefaultPassword(false);
            userRepository.save(user);
        }
    }



    @Override
    public Boolean checkUserExistOrNotWithUsername(String username) {
        if(Objects.equals(getUsername(), username)){
            return false;
        }
        return userRepository.findByUsername(username).isPresent();
    }


    @Override
    @PreAuthorize("hasAnyAuthority('PMO','DH','PM')")
    public User createMember(AddUserFormInput addUserFormInput, String defaultPassword){

        var currentUser = getCurrentUser();
        Department department = currentUser.getDepartment();
        Long departmentId = addUserFormInput.getDepartment();
        Role role  = Role.valueOf(addUserFormInput.getRole());

        if(departmentId!=null){ // Assuming getDepartment() returns the department ID
            department = (Department) departmentRepository.findById(toIntExact(departmentId))
                    .orElseThrow(() -> new RuntimeException("Department not found"));
        }
        if(currentUser.getRole()==Role.PM){
            role = Role.MEMBER;
        }

        log.info("addUserFormInput: {}\n\n\n\n\n", addUserFormInput);
        // Create a new User object based on the addUserFormInput
        User user =  User.builder()
                .name(addUserFormInput.getName())
                .email(addUserFormInput.getEmail())
                .role(role)
                .department(department)
                .defaultPassword(true)
                .active(true)
                .build();

        user.setPassword(passwordEncoder.encode(defaultPassword));

        User savedUser = userRepository.save(user);
//        sendDefaultPasswordEmail(savedUser, defaultPassword);
        return savedUser;
    }


    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM')")
    public List<UserViewObject> getAllUsers() {
        var currentUser = getCurrentUser();
        Role currentRole = currentUser.getRole();
        if(currentRole == Role.PMO||currentRole== Role.SDQC){
            return userRepository.findAll().stream()
                    .filter(val-> !Objects.equals(val.getId(), currentUser.getId()))
                    .map(UserViewObject::new).toList();
        }
        if(currentRole== Role.DH){
            return userRepository.findAllByDepartment(getCurrentUser().getDepartment()).stream()
                    .filter(val-> !Objects.equals(val.getId(), currentUser.getId()))
                    .filter(val-> !(val.getRole()==Role.PMO||val.getRole()==Role.SDQC))
                    .map(UserViewObject::new).toList();
        }
        if(currentRole== Role.PM){
            return userRepository.findAllByDepartmentAndRole(getCurrentUser().getDepartment(), Role.MEMBER)
                    .stream().filter(val-> !Objects.equals(val.getId(), currentUser.getId()))
                    .map(UserViewObject::new).toList();
        }
        return null;
    }

    @Override
    public User getUserById(Long id) {
        // Assuming you have a UserRepository or a database query to fetch the user by ID
        Optional<User> userOptional = userRepository.findById(id);
        // For this example, we return null if the user is not found
        return userOptional.orElse(null);
    }

    @Override
    public User getCurrentUser(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(username, username).orElse(null);
    }

    public Boolean checkCurrentPassword(String currentPassword) {
        User user = getCurrentUser();
        return passwordEncoder.matches(currentPassword, user.getPassword());
    }

    private String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    @Override
    public Optional<User> getEmail(String email) {
        return userRepository.findByEmail(email);
    }



}
