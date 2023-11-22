package org.blank.projectmanagementsystem;

import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.ArchitectureType;
import org.blank.projectmanagementsystem.domain.Enum.NotificationType;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.repository.*;
import org.blank.projectmanagementsystem.service.NotificationService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@Slf4j
public class ProjectManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementSystemApplication.class, args);

    }


//    @Bean
    CommandLineRunner runner(
            UserService userService, PasswordEncoder passwordEncoder,
            ProjectService projectService,
            ClientRepository clientRepository,
            SystemOutlineRepository systemOutlineRepository,
            ArchitectureRepository architectureRepository,
            DeliverableRepository deliverableRepository,
            NotificationService notificationService
    ) {
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
                            .email("pm@gmail.com")
                            .password(passwordEncoder.encode("Khun1234"))
                            .role(Role.PM)
                            .department(department2)
                            .active(true)
                            .build()
            );

            var sdqc = userService.save(
                    User.builder()
                            .name("Software Developer Quality Control")
                            .username("sdqc")
                            .email("sdqc@gmail.com")
                            .password(passwordEncoder.encode("Khun1234"))
                            .role(Role.SDQC)
                            .department(department2)
                            .active(true)
                            .build()
            );
            var member = userService.save(
                    User.builder()
                            .name("Member")
                            .username("member")
                            .email("member@gmail.com")
                            .password(passwordEncoder.encode("Khun1234"))
                            .role(Role.MEMBER)
                            .department(department2)
                            .active(true)
                            .build()
            );

            var client = clientRepository.save(
                    Client.builder()
                            .name("DAT")
                            .email("dat@dat.com")
                            .phoneNumber("123456789")
                            .build()
            );




            var systemOutlines = systemOutlineRepository.save(
                    SystemOutline.builder().id(null).name("BasicDesign").build()
            );
            var architectureOutline = architectureRepository.save(
                    Architecture.builder().id(null).name("Java").type(ArchitectureType.PROGRAMMING_LANGUAGE).build()
            );
            var deliverable = deliverableRepository.save(
                    Deliverable.builder().id(null).name("Deliverable 1").build()
            );

        };
    }
}


