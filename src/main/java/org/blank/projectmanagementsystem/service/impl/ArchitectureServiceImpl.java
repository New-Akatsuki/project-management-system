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
    public Architecture getById(int id) {

        return architectureRepository.findById(id).orElse(null);
    }

    @Override
    public List<Architecture> getAllArchitectures() {
        return architectureRepository.findAll();
    }

    @Override
    public Architecture saveArchitecture(Architecture architecture) {
        return architectureRepository.save(architecture);
    }

    @Override
    public void deleteArchitecture(int id) {
        architectureRepository.deleteById(id);
    }

    @Override
    public Architecture updateArchitecture(int id, Architecture architecture) {
        if (architectureRepository.existsById(id)) {
            architecture.setId(id);
            return architectureRepository.save(architecture);
        }
        return null;
    }

    @Override
    public void save(Architecture architecture) {
        architectureRepository.save(architecture);

    }


}

