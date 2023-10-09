package org.blank.projectmanagementsystem;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.Role;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProjectManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserService userService, PasswordEncoder passwordEncoder) {
        return args -> {
            //create role and department
            Role admin = new Role(null, "ADMIN");
            Role user = new Role(null, "USER");
            userService.saveRole(admin);
            userService.saveRole(user);

            Department department = new Department(null, "IT", true);
            Department department2 = new Department(null, "HR", true);
            userService.saveDepartment(department);
            userService.saveDepartment(department2);

            // save demo data after start
            var user1 = userService.save(
                    User.builder()
                            .name("John")
                            .username("john@gmail.com")
                            .password(passwordEncoder.encode("1234"))
                            .role(admin)
                            .department(department2)
                            .active(true)
                            .build()
            );

            var user2 = userService.save(
                    User.builder()
                            .name("mike")
                            .username("mike@gmail.com")
                            .password(passwordEncoder.encode("1234"))
                            .role(user)
                            .department(department)
                            .active(true)
                            .build()
            );

        };
    }
}


