package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Deliverable;

public interface DeliverableService {
    Deliverable createDeliverable(Deliverable deliverable);
    Deliverable updateDeliverable(Deliverable deliverable);
    void deleteDeliverable(int id);
    Deliverable getDeliverable(int id);



}
