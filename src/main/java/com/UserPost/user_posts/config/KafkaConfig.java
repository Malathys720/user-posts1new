package com.UserPost.user_posts.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;


@Configuration
public class KafkaConfig {

    @Value("${kafka.topic:user-posts}")
    private String topic;

    @Bean
    public NewTopic userPostsTopic() {
        return new NewTopic(topic, 1, (short) 1);
    }
}
