package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.mapper.ProjectMapper;
import org.blank.projectmanagementsystem.repository.*;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public Project saveProject(ProjectFormInput projectFormInput) {
        //get project manager data
        String pmUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User projectManager = userRepository.findByUsernameOrEmail(pmUsername,pmUsername).orElseThrow();

        //get client data
        Client client = clientRepository.findById(projectFormInput.getClient()).orElseThrow();

        //get contract memebers
        List<Long> contractMembersIds = projectFormInput.getContractMembers();
        List<User> contractMembers = new ArrayList<>();
        if(contractMembersIds!=null && contractMembersIds.size() > 0){
            contractMembers = contractMembersIds.stream()
                    .map(id -> userRepository.findById(id).orElse(null)).toList();
        }

        //get foc members
        List<Long> focMembersIds = projectFormInput.getFocMembers();
        List<User> focMembers = new ArrayList<>();
        if(focMembersIds!=null && focMembersIds.size() > 0){
            focMembers = focMembersIds.stream().map(id -> userRepository.findById(id)
                    .orElse(null)).toList();
        }

        //get SystemOutlines
        List<Integer> systemOutlineIDs = projectFormInput.getSystemOutlines();
        List<SystemOutline> systemOutlines = new ArrayList<>();
        if(systemOutlineIDs!=null && systemOutlineIDs.size() > 0){
            systemOutlines = systemOutlineIDs.stream().map(id-> systemOutlineRepository.findById(id)
                    .orElse(null)).toList();
        }

        //get Architecture outlines
        List<Integer> architectureIds = projectFormInput.getArchitectureOutlines();
        List<Architecture> architectures = new ArrayList<>();
        if(architectureIds!=null && architectureIds.size() > 0){
            architectures = architectureIds.stream().map(id-> architectureRepository.findById(id)
                    .orElse(null)).toList();
        }
        //get Deliverables
        List<Integer> deliverablesIds = projectFormInput.getDeliverables();
        List<Deliverable> deliverables = new ArrayList<>();
        if(deliverablesIds!=null && deliverablesIds.size() > 0){
            deliverables = deliverablesIds.stream().map(id-> deliverableRepository.findById(id)
                    .orElse(null)).toList();
        }

        Project project = projectMapper.mapToProject(projectFormInput);
        //set project manager to project
        project.setProjectManager(projectManager);
        //set client to project
        project.setClient(client);
        project.getContractMembers().addAll(contractMembers);
        project.getFocMembers().addAll(focMembers);
        project.getArchitectures().addAll(architectures);
        project.getSystemOutlines().addAll(systemOutlines);
        project.getDeliverables().addAll(deliverables);
        project.setDepartment(projectManager.getDepartment());

        return projectRepository.save(project);
    }

    @Override
    public List<ProjectViewObject> getAllProjects() {
//        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
//                .stream().map(GrantedAuthority::getAuthority).findFirst().orElse("");

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
}
