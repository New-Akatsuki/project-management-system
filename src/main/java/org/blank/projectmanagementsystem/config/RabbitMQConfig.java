//package org.blank.projectmanagementsystem.config;
//
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConfig {
//
//    @Value("${spring.rabbitmq.host}")
//    private String host;
//
//    @Value("${spring.rabbitmq.username}")
//    private String username;
//
//    @Value("${spring.rabbitmq.password}")
//    private String password;
//
//    @Value("${spring.rabbitmq.port}")
//    private int port;
//
//    @Value("${spring.rabbitmq.virtual-host}")
//    private String virtualHost;
//
//    @Bean
//    public DirectExchange directExchange() {
//        String directExchangeName = "pms-direct-exchange";
//        return new DirectExchange(directExchangeName);
//    }
//
//    @Bean
//    public CachingConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setHost(host);
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//        connectionFactory.setPort(port);
//        connectionFactory.setVirtualHost(virtualHost);
//        return connectionFactory;
//    }
//
//    @Bean
//    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//        return new RabbitAdmin(connectionFactory);
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        return new RabbitTemplate(connectionFactory);
//    }
//}
