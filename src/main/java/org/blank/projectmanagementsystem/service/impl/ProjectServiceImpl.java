package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.ProjectStatus;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.mapper.ProjectMapper;
import org.blank.projectmanagementsystem.repository.*;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    private final ProjectMapper projectMapper = new ProjectMapper();

    @Override
    public Project saveProject(ProjectFormInput projectFormInput, String pmUsername) {
        //get project manager data
//        String pmUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User projectManager = userRepository.findByUsernameOrEmail(pmUsername,pmUsername).orElseThrow();

        //get client data
        Client client = clientRepository.findById(projectFormInput.getClient()).orElseThrow();

        //get contract memebers
        List<Long> contractMembersIds = projectFormInput.getContractMembers();
        Set<User> contractMembers = new HashSet<>();
        if(contractMembersIds!=null && contractMembersIds.size() > 0){
            contractMembers = contractMembersIds.stream()
                    .map(id -> userRepository.findById(id).orElse(null)).collect(Collectors.toSet());
        }

        //get foc members
        List<Long> focMembersIds = projectFormInput.getFocMembers();
        Set<User> focMembers = new HashSet<>();
        if(focMembersIds!=null && focMembersIds.size() > 0){
            focMembers = focMembersIds.stream().map(id -> userRepository.findById(id)
                    .orElse(null)).collect(Collectors.toSet());
        }

        //get SystemOutlines
        List<Long> systemOutlineIDs = projectFormInput.getSystemOutlines();
        Set<SystemOutline> systemOutlines = new HashSet<>();
        if(systemOutlineIDs!=null && systemOutlineIDs.size() > 0){
            systemOutlines = systemOutlineIDs.stream().map(id-> systemOutlineRepository.findById(id)
                    .orElse(null)).collect(Collectors.toSet());
        }

        //get Architecture outlines
        List<Long> architectureIds = projectFormInput.getArchitectureOutlines();

        Set<Architecture> architectures = new HashSet<>();

        if(architectureIds!=null && architectureIds.size() > 0){
            architectures = architectureIds.stream().map(id-> architectureRepository.findById(id)
                    .orElse(null)).collect(Collectors.toSet());
        }
        //get Deliverables
        List<Long> deliverablesIds = projectFormInput.getDeliverables();

        Set<Deliverable> deliverables = new HashSet<>();

        if(deliverablesIds!=null && deliverablesIds.size() > 0){
            deliverables = deliverablesIds.stream().map(id-> deliverableRepository.findById(id)
                    .orElse(null)).collect(Collectors.toSet());
        }

        Project project = projectMapper.mapToProject(projectFormInput);
        //set project manager to project
        project.setProjectManager(projectManager);
        //set client to project
        project.setClient(client);
        project.setContractMembers(contractMembers);
        project.setFocMembers(focMembers);
        project.setArchitectures(architectures);
        project.setSystemOutlines(systemOutlines);
        project.setDeliverables(deliverables);
        project.setDepartment(projectManager.getDepartment());

        return projectRepository.save(project);
    }

    @Override
    public List<ProjectViewObject> getAllProjects() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsernameOrEmail(username,username).orElseThrow();

        return switch (user.getRole()) {
            case PMO,SDQC -> projectRepository.findAll().stream().map(ProjectViewObject::new).toList();
            case DH-> projectRepository.findAllByDepartment(user.getDepartment()).map(p->new ProjectViewObject((Project) p)).stream().toList();
            case PM ->projectRepository.findAllByProjectManager(user).map(p->new ProjectViewObject((Project) p)).stream().toList();
            case MEMBER-> projectRepository.findAllProjectsByUserInMembers(user).map(p->new ProjectViewObject((Project) p)).stream().toList();
            default -> throw new IllegalStateException("Invalid user");
        };
    }
    @Override
    public List<User> getProjectMembers(Long projectId) {
        List<User> members = new ArrayList<>();
        projectRepository.findById(projectId).
                ifPresent(val->{
                    members.addAll(val.getContractMembers());
                    members.addAll(val.getFocMembers());
                    members.add(val.getProjectManager());
                });
        return members;
    }
    @Override
    public List<User> getUsersByOngoingProject() {
        var projects = projectRepository.findAllProjectsByUserInMembersAndStatus(getCurrentUser(), ProjectStatus.ONGOING.name());
        List<User> users = new ArrayList<>();
        projects.ifPresent(projectList->{
            projectList.forEach(project -> {
               users.addAll(getProjectMembers(project.getId()));
            });
        });
        return users;
    }
    @Override
    public Map<String,List<Object>> getUsersAndClientByOngoingProject() {
        var projects = projectRepository.findAllProjectsByUserInMembersAndStatus(getCurrentUser(), ProjectStatus.ONGOING.name());
        Map<String,List<Object>> data = new HashMap<>();
        List<Object> users = new ArrayList<>();
        List<Object> clients = new ArrayList<>();

        projects.ifPresent(projectList->{
            projectList.forEach(project -> {
                users.addAll(getProjectMembers(project.getId()));
                clients.add(project.getClient());
            });
        });
        data.put("users",users);
        data.put("clients",clients);
        return data;
    }

    private User getCurrentUser(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(username,username).orElseThrow();
    }
}
