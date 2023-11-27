package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Deliverable;

import java.util.List;

public interface DeliverableService {
    Deliverable save(Deliverable deliverable);
    Deliverable findById(long id);
    List<Deliverable> getAllDeliverables();
    List<Deliverable> getDeliverablesByStatusTrue();
    Deliverable getDeliverableById(Long id);

}
