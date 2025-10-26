package com.UserPost.user_posts.service;

import com.UserPost.user_posts.dto.MergedPostDto;
import com.UserPost.user_posts.dto.PostDto;
import com.UserPost.user_posts.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class GatherService {

    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String usersUrl = "https://jsonplaceholder.typicode.com/users";
    private final String postsUrl = "https://jsonplaceholder.typicode.com/posts";
    private final String topic;


    public GatherService(RestTemplate restTemplate,
                         KafkaTemplate<String, Object> kafkaTemplate,
                         @Value("${kafka.topic:user-posts}") String topic) {
        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }
    public int gatherAndSend() throws Exception {
        // Use virtual-thread-per-task executor (Java 21)
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<List<UserDto>> usersFuture = executor.submit(this::fetchUsers);
            Future<List<PostDto>> postsFuture = executor.submit(this::fetchPosts);

            List<UserDto> users = usersFuture.get();
            System.out.println("***************"+users);// wait for completion
            List<PostDto> posts = postsFuture.get();
            System.out.println("****************"+posts);

            Map<Integer, UserDto> userById = users.stream()
                    .filter(u -> u.getId() != null)
                    .collect(Collectors.toMap(UserDto::getId, u -> u));

            List<MergedPostDto> merged = posts.stream()
                    .filter(p -> p.getTitle() != null)
                    .filter(p -> p.getTitle().length() <= 200) // exclude titles > 200
                    .map(p -> {
                        UserDto user = userById.get(p.getUserId());
                        String userName = user != null ? user.getName() : null;
                        String userEmail = user != null ? user.getEmail() : null;
                        return new MergedPostDto(p.getId(), userName, userEmail, p.getTitle(), p.getBody());
                    })
                    .collect(Collectors.toList());

            // send to kafka asynchronously (fire-and-forget)
            for (MergedPostDto mp : merged) {
                kafkaTemplate.send(topic, String.valueOf(mp.getPostId()), mp);
            }
            return merged.size();
        }
    }




    private List<UserDto> fetchUsers() {
        UserDto[] arr = restTemplate.getForObject(usersUrl, UserDto[].class);
        return arr == null ? Collections.emptyList() : Arrays.asList(arr);
    }

    private List<PostDto> fetchPosts() {
        PostDto[] arr = restTemplate.getForObject(postsUrl, PostDto[].class);
        return arr == null ? Collections.emptyList() : Arrays.asList(arr);
    }

}
