package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Deliverable;

import java.util.List;

public interface DeliverableService {
    Deliverable createDeliverable(Deliverable deliverable);
    Deliverable updateDeliverable(Deliverable deliverable);
    void deleteDeliverable(Long id);
    Deliverable getDeliverable(Long id);

    List<Deliverable> getAllDeliverables();

    List<Deliverable> getAllDeliverable();
}
