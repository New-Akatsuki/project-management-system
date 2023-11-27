package org.blank.projectmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.repository.DeliverableRepository;
import org.blank.projectmanagementsystem.service.DeliverableService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class DeliverableServiceImpl implements DeliverableService {

    private final DeliverableRepository deliverableRepository;

    public DeliverableServiceImpl(DeliverableRepository deliverableRepository) {
        this.deliverableRepository = deliverableRepository;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public Deliverable save(Deliverable deliverable) {

        return deliverableRepository.save(deliverable);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public Deliverable getDeliverableById(Long id) {
        Optional<Deliverable> optionalDeliverable = deliverableRepository.findById(id);
        return optionalDeliverable.orElse(null);
    }


    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public Deliverable findById(long id) {
        return deliverableRepository.findById(id).orElse(null);
    }


    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public List<Deliverable> getAllDeliverables() {
        return deliverableRepository.findAll();
    }

    @Override
    public List<Deliverable> getDeliverablesByStatusTrue() {
        return deliverableRepository.findByStatusIsTrue();
    }


}
