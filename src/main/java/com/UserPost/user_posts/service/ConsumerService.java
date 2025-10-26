package com.UserPost.user_posts.service;


import com.UserPost.user_posts.dto.MergedPostDto;
import com.UserPost.user_posts.entity.UserPostEntity;
import com.UserPost.user_posts.repository.UserPostRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private final UserPostRepository repository;

    public ConsumerService(UserPostRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "${kafka.topic:user-posts}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(MergedPostDto mp) {
        if (mp == null || mp.getPostId() == null) return;

        UserPostEntity entity = new UserPostEntity(
                mp.getPostId(),
                mp.getUserName(),
                mp.getUserEmail(),
                mp.getPostTitle(),
                mp.getPostBody()
        );
        repository.save(entity);
    }

}
