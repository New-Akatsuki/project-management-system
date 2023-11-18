package org.blank.projectmanagementsystem.service;


import org.springframework.stereotype.Service;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import java.util.List;

@Service
public interface ArchitectureService {

    List<Architecture> getAllArchitectures();

    Architecture updateArchitecture(Long id, Architecture architecture);
    Architecture save(Architecture architecture);
    Architecture getArchitectureById(Long id);
}
