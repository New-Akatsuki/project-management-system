package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ChangePasswordFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectListViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.*;
import org.blank.projectmanagementsystem.service.impl.NotificationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PMRestController {
    private final UserService userService;
  
    @PostMapping("/pm/add-users")
    public ResponseEntity<AddUserFormInput> addUser(@RequestBody AddUserFormInput addUserFormInput) {
        User newUser = userService.createMember(addUserFormInput);

        return ResponseEntity.ok(addUserFormInput);
    }

    @GetMapping("/pm/users")
    public ResponseEntity<List<UserViewObject>> getUserList() {
        List<UserViewObject> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @PostMapping("/pm/change-password")
    public ResponseEntity<ChangePasswordFormInput> changePassword(@RequestBody ChangePasswordFormInput changePasswordFormInput) {
        ChangePasswordFormInput chpwd = userService.changePassword(
                changePasswordFormInput.getCurrentPassword(),
                changePasswordFormInput.getNewPassword());
        log.info("===========================================================================================");
        log.info("ChangePasswordFormInput: {}", chpwd);
        log.info("===========================================================================================");
        if (chpwd != null) {
            chpwd.setCurrentPassword(changePasswordFormInput.getCurrentPassword());
            chpwd.setNewPassword(changePasswordFormInput.getNewPassword());
            chpwd.setConfirmPassword(changePasswordFormInput.getConfirmPassword());
            return ResponseEntity.ok(chpwd);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/pm/check-current-password")
    public ResponseEntity<String> checkCurrentPassword(@RequestBody ChangePasswordFormInput changePasswordFormInput) {
        boolean chpwd = userService.checkCurrentPassword(changePasswordFormInput.getCurrentPassword());
        log.info("===========================================================================================");
        log.info("CurrentPassword: {}", chpwd);
        log.info("===========================================================================================");

        if (chpwd) {
            String cp = changePasswordFormInput.getCurrentPassword();
            return ResponseEntity.ok(cp);
        } else {
            return ResponseEntity.notFound().build();
        }

    }


    @PostMapping("/pm/create-proj")
    public ResponseEntity<Project> createProject(@RequestBody ProjectFormInput projectFormInput) {
        log.info("create project {} \n\n", projectFormInput);
        return ResponseEntity.ok(projectService.saveProject(projectFormInput));
    }

    @GetMapping("/get-department")
    public List<Department> getDepartment() {
        List<Department> department = departmentService.getAllDepartments();
        log.info("get department {} \n\n", department);
        return department;
    }

    @GetMapping("/get-member")
    public List<UserViewObject> getMember() {
        List<UserViewObject> member = userService.getAllUsers();
        log.info("get user{} \n\n", member);
        return member;
    }

    @GetMapping("/get-deliverables")
    public List<Deliverable> getDeli() {
        List<Deliverable> deli= deliverableService.getAllDeliverables();
        log.info("get deli {} \n\n", deli);
        return deli;
    }

    @GetMapping("/get-architecture-outlines")
    public List<Architecture> getArchi() {
        List<Architecture> archi= architectureService.getAllArchitectures();
        log.info("get archi{} \n\n", archi);
        return archi;
    }

    @GetMapping("/get-systemoutlines")
    public List<SystemOutline> getSystem() {
        List<SystemOutline> system= systemOutlineService.getAllSystemOutlines();
        log.info("get system{} \n\n", system);
        return system;
    }

    @GetMapping("/get-client")
    public List<Client> getClient() {
        List<Client> system= clientService.getAllClients();
        log.info("get system{} \n\n", system);
        return system;
    }

    @GetMapping("/get-projects")
    public List<ProjectListViewObject> getProject() {
        List<ProjectListViewObject> proj= projectService.getAllProjects();
        log.info("get proj{} \n\n", proj);
        return proj;
    }

}




