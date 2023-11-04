package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Deliverable;

import java.util.List;

public interface DeliverableService {
    Deliverable createDeliverable(Deliverable deliverable);
    Deliverable updateDeliverable(Deliverable deliverable);
    void deleteDeliverable(long id);
    Deliverable getDeliverable(long id);
    Deliverable save(Deliverable deliverable);

    Deliverable findById(long id);
    Deliverable saveName(String name);


    List<Deliverable> getAllDeliverables();

    List<Deliverable> getAllDeliverable();

    Deliverable getDeliverableById(Long id);
}
