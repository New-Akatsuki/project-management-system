package org.blank.projectmanagementsystem.service;


import org.springframework.stereotype.Service;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import java.util.List;

@Service
public interface ArchitectureService {
    Architecture getById(Long id);
   List<Architecture> getAllArchitectures();
    Architecture saveArchitecture(Architecture architecture);
    void deleteArchitecture(Long id);
    Architecture updateArchitecture(Long id, Architecture architecture);

}
