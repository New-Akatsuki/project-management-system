package org.blank.projectmanagementsystem;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class ProjectManagementSystemApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(ProjectManagementSystemApplication.class, args);
        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }

    @Bean
    CommandLineRunner runner(UserService userService, PasswordEncoder passwordEncoder) {
        return args -> {

            Department department = new Department(null, "IT", true);
            Department department2 = new Department(null, "HR", true);
            userService.saveDepartment(department);
            userService.saveDepartment(department2);

            // save demo data after start
            var pmo = userService.save(
                    User.builder()
                            .name("Project Manager Officer")
                            .username("pmo")
                            .email("pmo@gmail.com")
                            .password(passwordEncoder.encode("Khun1234"))
                            .role(Role.PMO)
                            .department(department2)
                            .active(true)
                            .build()
            );

            var dh = userService.save(
                    User.builder()
                            .name("Department Head")
                            .username("dh")
                            .email("dh@gmail.com")
                            .password(passwordEncoder.encode("Khun1234"))
                            .role(Role.DH)
                            .department(department)
                            .active(true)
                            .build()
            );

            var pm = userService.save(
                    User.builder()
                            .name("Project Manager")
                            .username("pm")
                            .email("kaprnya@gmail.com")
                            .password(passwordEncoder.encode("1234"))
                            .role(Role.PM)
                            .department(department2)
                            .active(true)
                            .build()
            );

            var sdqc = userService.save(
                    User.builder()
                            .name("Software Developer Quality Control")
                            .username("sdqc")
                            .email("jokershay@gmail.com")
                            .password(passwordEncoder.encode("1234"))
                            .role(Role.SDQC)
                            .department(department2)
                            .active(true)
                            .build()
            );
            var member = userService.save(
                    User.builder()
                            .name("Member")
                            .username("member")
                            .email("mm@gmail.com")
                            .password(passwordEncoder.encode("1234"))
                            .role(Role.MEMBER)
                            .department(department2)
                            .active(true)
                            .build()
            );

        };
    }
}


