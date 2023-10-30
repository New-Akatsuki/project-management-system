package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.repository.QueueInfoRepository;
import org.blank.projectmanagementsystem.service.QueueInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class QueueInfoServiceImpl implements QueueInfoService {

    private final QueueInfoRepository queueInfoRepository;

    @Transactional(readOnly = true)
    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public String getRoutingKeyByUsername(String username) {
        return queueInfoRepository.findRoutingKeyByUsername(username).orElseThrow(() -> new RuntimeException("Queue not found."));
    }

    @Transactional(readOnly = true)
    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public String getQueueNameByUsername(String username) {
        return queueInfoRepository.findQueueNameByUsername(username);
    }


}
