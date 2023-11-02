package org.blank.projectmanagementsystem.service;


import org.springframework.stereotype.Service;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import java.util.List;

@Service
public interface ArchitectureService {
    Architecture getById(int id);
   List<Architecture> getAllArchitectures();
    Architecture saveArchitecture(Architecture architecture);
    void deleteArchitecture(int id);
    Architecture updateArchitecture(int id, Architecture architecture);
    Architecture save(Architecture architecture);
}
