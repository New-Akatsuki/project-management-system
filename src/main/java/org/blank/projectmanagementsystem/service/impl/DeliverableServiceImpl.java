package org.blank.projectmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.repository.DeliverableRepository;
import org.blank.projectmanagementsystem.service.DeliverableService;
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
    public Deliverable createDeliverable(Deliverable deliverable) {
        return deliverableRepository.save(deliverable);
    }

    @Override
    public Deliverable updateDeliverable(Deliverable deliverable) {
        return deliverableRepository.save(deliverable);
    }

    @Override
    public void deleteDeliverable(long id) {
        deliverableRepository.deleteById(id);
    }

    @Override
    public Deliverable getDeliverable(Long id) {
        return deliverableRepository.findById(id).orElse(null);
    }

    @Override
    public Deliverable save(Deliverable deliverable) {

        return deliverableRepository.save(deliverable);
    }

    @Override
    public List<Deliverable> getAllDeliverable() {
        return deliverableRepository.findAll();
    }

    @Override
    public Deliverable getDeliverableById(Long id) {
        Optional<Deliverable> optionalDeliverable = deliverableRepository.findById(id);
        return optionalDeliverable.orElse(null);
    }


    @Override
    public Deliverable findById(long id) {
        return deliverableRepository.findById(id).orElse(null);
    }

    @Override
    public Deliverable saveName(String name) {
        Deliverable deliverable = new Deliverable();
        deliverable.setName(name);
        deliverableRepository.save(deliverable);
        return deliverable;
    }

    @Override
    public List<Deliverable> getAllDeliverables() {
        return deliverableRepository.findAll();
    }


}
