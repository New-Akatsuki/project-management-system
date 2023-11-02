package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;

import java.util.List;
import java.util.Optional;

public interface DeliverableService {
    Deliverable createDeliverable(Deliverable deliverable);
    Deliverable updateDeliverable(Deliverable deliverable);
    void deleteDeliverable(int id);
    Deliverable getDeliverable(int id);
    void save(Deliverable deliverable);

    Deliverable findById(int id);
    Deliverable saveName(String name);


    List<Deliverable> getAllDeliverables();

    List<Deliverable> getAllDeliverable();
}
