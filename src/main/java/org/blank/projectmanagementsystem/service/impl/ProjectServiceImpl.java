    package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.ProjectStatus;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectEditViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectListViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.mapper.ProjectMapper;
import org.blank.projectmanagementsystem.repository.*;
import org.blank.projectmanagementsystem.service.NotificationService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final SystemOutlineRepository systemOutlineRepository;
    private final ArchitectureRepository architectureRepository;
    private final DeliverableRepository deliverableRepository;
    private final TaskRepository taskRepository;

    private final ProjectMapper projectMapper = new ProjectMapper();

    @Override
    public Project saveProject(ProjectFormInput projectFormInput) {

        String pmUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = getCurrentUser();
        User projectManager = userRepository.findByUsernameOrEmail(pmUsername,pmUsername).orElseThrow();

        //get client data
        Client client = clientRepository.findById(projectFormInput.getClient()).orElseThrow();

        //get contract memebers
        List<Long> contractMembersIds = projectFormInput.getContractMembers();
        Set<User> contractMembers = new HashSet<>();
        if (contractMembersIds != null && contractMembersIds.size() > 0) {
            contractMembers = contractMembersIds.stream()
                    .map(id -> userRepository.findById(id).orElse(null)).collect(Collectors.toSet());
        }

        //get foc members
        List<Long> focMembersIds = projectFormInput.getFocMembers();
        Set<User> focMembers = new HashSet<>();
        if (focMembersIds != null && focMembersIds.size() > 0) {
            focMembers = focMembersIds.stream().map(id -> userRepository.findById(id)
                    .orElse(null)).collect(Collectors.toSet());
        }

        //get SystemOutlines
        List<Long> systemOutlineIDs = projectFormInput.getSystemOutlines();
        Set<SystemOutline> systemOutlines = new HashSet<>();
        if (systemOutlineIDs != null && systemOutlineIDs.size() > 0) {
            systemOutlines = systemOutlineIDs.stream().map(id -> systemOutlineRepository.findById(id)
                    .orElse(null)).collect(Collectors.toSet());
        }

        //get Architecture outlines
        List<Long> architectureIds = projectFormInput.getArchitectureOutlines();

        Set<Architecture> architectures = new HashSet<>();

        if (architectureIds != null && architectureIds.size() > 0) {
            architectures = architectureIds.stream().map(id -> architectureRepository.findById(id)
                    .orElse(null)).collect(Collectors.toSet());
        }
        //get Deliverables
        List<Long> deliverablesIds = projectFormInput.getDeliverables();

        Set<Deliverable> deliverables = new HashSet<>();

        if (deliverablesIds != null && deliverablesIds.size() > 0) {
            deliverables = deliverablesIds.stream().map(id -> deliverableRepository.findById(id)
                    .orElse(null)).collect(Collectors.toSet());
        }
        Project project = null;
        project = projectMapper.mapToProject(projectFormInput);
        //set project manager to project
        project.setProjectManager(projectManager);
        //set client to project
        project.setClient(client);
        project.setContractMembers(contractMembers);
        project.setFocMembers(focMembers);
        project.setArchitectures(architectures);
        project.setSystemOutlines(systemOutlines);
        project.setDeliverables(deliverables);
        project.setDepartment(user.getDepartment());
        project.setStatus(ProjectStatus.ONGOING);
        return projectRepository.save(project);
    }

    @Override
    public List<ProjectListViewObject> getAllProjects() {
        var user = getCurrentUser();
        return switch (user.getRole()) {
            case PMO, SDQC -> projectRepository.findAll().stream().map(p->new ProjectListViewObject(p,countProgress(p.getId()))).toList();
            case DH ->
                    projectRepository.findAllByDepartment(user.getDepartment()).stream().map(p->new ProjectListViewObject(p,countProgress(p.getId()))).toList();
            case PM ->
                    projectRepository.findAllByProjectManager(user).stream().map(p->new ProjectListViewObject(p,countProgress(p.getId()))).toList();
            case MEMBER ->
                    projectRepository.findAllOngoingProjectsByUserInMembers(user).stream().map(p->new ProjectListViewObject(p,countProgress(p.getId()))).toList();
        };
    }

    //count project progress
    private int countProgress(Long id) {
        int total = 0;
        int done = 0;
        var tasks = taskRepository.findAllByProjectId(id).stream().filter(val->val.getParentTask()==null).toList();
        total = tasks.size();
        if (total == 0) return 0;
        done = tasks.stream().filter(Task::isStatus).toList().size();
        return (int) (((float)done/total)*100);
    }


    @Override
    public ProjectViewObject getProjectById(Long id) {
        return new ProjectViewObject(projectRepository.getReferenceById(id));
    }

    @Override
    public Project getReferenceById(Long id) {
        return projectRepository.getReferenceById(id);
    }

    @Override
    public List<User> getProjectMembers(Long projectId) {
        List<User> members = new ArrayList<>();
        projectRepository.findById(projectId).
                ifPresent(val -> {
                    members.addAll(val.getContractMembers());
                    members.addAll(val.getFocMembers());
                    members.add(val.getProjectManager());
                });
        return members;
    }

    @Override
    public List<User> getUsersByOngoingProject() {
        var projects = projectRepository.findAllProjectsByUserInMembersAndStatus(getCurrentUser(), ProjectStatus.ONGOING);
        List<User> users = new ArrayList<>();
        projects.ifPresent(projectList -> {
            projectList.forEach(project -> {
                users.addAll(getProjectMembers(project.getId()));
            });
        });
        return users.stream().distinct().toList();
    }

    @Override
    public Map<String, List<Object>> getUsersAndClientByOngoingProject() {
        var projects = projectRepository.findAllProjectsByUserInMembersAndStatus(getCurrentUser(), ProjectStatus.ONGOING);
        Map<String, List<Object>> data = new HashMap<>();
        List<Object> users = new ArrayList<>();
        List<Object> clients = new ArrayList<>();

        projects.ifPresent(projectList -> {
            projectList.forEach(project -> {
                users.addAll(getProjectMembers(project.getId()));
                clients.add(project.getClient());
            });
        });
        data.put("users", users);
        data.put("clients", clients);
        return data;
    }

    @Override
    public List<ProjectViewObject> getProjectsByDepartment(Integer departmentId) {
        return projectRepository.findByDepartmentId(departmentId).stream().map(ProjectViewObject::new).toList();
    }

    @Override
    public Project getProject(long projectId) {
        return projectRepository.getReferenceById(projectId);
    }

    @Override
    public ProjectEditViewObject getProjectByID(Long id) {
        return new ProjectEditViewObject(projectRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public String toggleProjectStatus(Long id) {
        var project = projectRepository.getReferenceById(id);
        if(project.getStatus()==ProjectStatus.PENDING){
            if (countProgress(id) != 100) {
                project.setStatus(ProjectStatus.ONGOING);
            } else {
                project.setStatus(ProjectStatus.FINISHED);
            }
        }else {
            project.setStatus(ProjectStatus.PENDING);
        }
        return project.getStatus().name();
    }

    private User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(username, username).orElseThrow();
    }

}
