package org.blank.projectmanagementsystem.service;

public interface QueueInfoService {

//    String getRoutingKeyByUsername(String username);
//
//    String getQueueNameByUsername(String username);

    String getQueueNameById(Long id);
    String getRoutingKeyById(Long id);
}
