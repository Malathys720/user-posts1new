package com.UserPost.user_posts.controller;


import com.UserPost.user_posts.dto.MergedPostDto;
import com.UserPost.user_posts.entity.UserPostEntity;
import com.UserPost.user_posts.repository.UserPostRepository;
import com.UserPost.user_posts.service.GatherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-posts")
public class UserPostController {

    private final GatherService gatherService;
    private final UserPostRepository repository;

    public UserPostController(GatherService gatherService, UserPostRepository repository) {
        this.gatherService = gatherService;
        this.repository = repository;
    }

    @PostMapping("/gather")
    public ResponseEntity<String> gather() {
        try {
            int sent = gatherService.gatherAndSend();
            return ResponseEntity.accepted().body("Sent " + sent + " merged posts to messaging system");
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error during gather: " + ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<MergedPostDto>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable p = PageRequest.of(Math.max(0, page), Math.max(1, size));
        Page<UserPostEntity> pageResult = repository.findAll(p);
        List<MergedPostDto> result = pageResult.stream()
                .map(e -> new MergedPostDto(e.getPostId(), e.getUserName(), e.getUserEmail(), e.getPostTitle(), e.getPostBody()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

}
