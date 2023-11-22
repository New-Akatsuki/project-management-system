package org.blank.projectmanagementsystem.service;


import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import java.util.List;

@Service
public interface ArchitectureService {

    List<Architecture> getAllArchitectures();
    Architecture save(Architecture architecture);
    Architecture getArchitectureById(Long id);
    Architecture findById(long id);
   // Architecture updateArchitecture(Long id, Architecture architecture);




}

