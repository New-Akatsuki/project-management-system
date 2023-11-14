//package org.blank.projectmanagementsystem.service;
//
//import lombok.RequiredArgsConstructor;
//import org.blank.projectmanagementsystem.domain.entity.QueueInfo;
//import org.blank.projectmanagementsystem.domain.entity.User;
//import org.blank.projectmanagementsystem.repository.QueueInfoRepository;
//import org.blank.projectmanagementsystem.repository.UserRepository;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class DynamicQueueManager {
//
//    private final RabbitAdmin rabbitAdmin;
//    private final QueueInfoRepository queueInfoRepository;
//    private final UserRepository userRepository;
//    private final DirectExchange directExchange;
//
//    public void createQueueForUser(String username) {
//        // create queue name
//        String queueName = "pms" + '.' + username + '.' + "queue";
//
//        // create binding key
//        String routingKey = "pms" + '.' + username + '.' + "key";
//
//        Queue queue = new Queue(queueName, true, false, false);
//        rabbitAdmin.declareQueue(queue);
//
//        // find user
//        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found."));
//
//        // save the queue info
//        queueInfoRepository.save(QueueInfo.builder()
//                .queueName(queueName)
//                .routingKey(routingKey)
//                .user(user)
//                .build());
//
//        // Create binding between queue and exchange
//        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(directExchange).with(routingKey));
//
//    }
//}
