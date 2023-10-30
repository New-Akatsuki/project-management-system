package org.blank.projectmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.repository.DeliverableRepository;
import org.blank.projectmanagementsystem.service.DeliverableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void deleteDeliverable(int id) {
        deliverableRepository.deleteById(id);
    }

    @Override
    public Deliverable getDeliverable(int id) {
        return deliverableRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Deliverable deliverable) {

    }

    @Override
    public List<Deliverable> getAllDeliverable() {
        return deliverableRepository.findAll();
    }


    @Override
    public Deliverable findById(int id) {
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
