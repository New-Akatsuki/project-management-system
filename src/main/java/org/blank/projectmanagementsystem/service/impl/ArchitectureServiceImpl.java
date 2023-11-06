package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.blank.projectmanagementsystem.repository.ArchitectureRepository;
import org.blank.projectmanagementsystem.service.ArchitectureService;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class ArchitectureServiceImpl implements ArchitectureService {
    private final ArchitectureRepository architectureRepository;

    @Override
    public Architecture getById(Long id) {

        return architectureRepository.findById(id).orElse(null);
    }




    @Override
    public List<Architecture> getAllArchitectures() {
        return architectureRepository.findAll();
    }



    @Override
    public void deleteArchitecture(Long id) {
        architectureRepository.deleteById(id);
    }

    @Override
    public Architecture updateArchitecture(Long id, Architecture architecture) {
        if (architectureRepository.existsById(id)) {
            architecture.setId(id);
            return architectureRepository.save(architecture);
        }
        return null;
    }

    @Override
    public Architecture save(Architecture architecture) {
        return architectureRepository.save(architecture);
    }

    @Override
    public Architecture getArchitectureById(Long id) {
        return architectureRepository.findById(id).orElse(null);
    }


}

