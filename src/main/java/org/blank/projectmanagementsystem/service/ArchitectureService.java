package org.blank.projectmanagementsystem.service;


import org.springframework.stereotype.Service;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import java.util.List;

@Service
public interface ArchitectureService {
    Architecture getById(Long id);
   List<Architecture> getAllArchitectures();

    void deleteArchitecture(Long id);
    Architecture updateArchitecture(Long id, Architecture architecture);
    Architecture save(Architecture architecture);

    Architecture getArchitectureById(Long id);
}
