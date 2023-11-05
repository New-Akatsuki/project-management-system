package org.blank.projectmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.repository.DeliverableRepository;
import org.blank.projectmanagementsystem.service.DeliverableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class DeliverableServiceImpl implements DeliverableService {

    private final DeliverableRepository deliverableRepository;

    public DeliverableServiceImpl(DeliverableRepository deliverableRepository) {
        this.deliverableRepository = deliverableRepository;
    }

    @Override
    public Deliverable createDeliverable(Deliverable deliverable) {
        return deliverableRepository.save(deliverable);
    }

    @Override
    public Deliverable updateDeliverable(Deliverable deliverable) {
        return deliverableRepository.save(deliverable);
    }

    @Override
    public void deleteDeliverable(Long id) {
        deliverableRepository.deleteById(id);
    }

    @Override
    public Deliverable getDeliverable(Long id) {
        return deliverableRepository.findById(id).orElse(null);
    }
}
