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

    @Override
    public String getQueueNameById(Long id) {
        return queueInfoRepository.findQueueNameById(id);
    }

    @Override
    public String getRoutingKeyById(Long id) {
        return queueInfoRepository.findRoutingKeyById(id);
    }


}
