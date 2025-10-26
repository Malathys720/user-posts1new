package com.UserPost.user_posts.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user_posts", indexes = {
        @Index(columnList = "postId", name = "idx_post_id")
})
public class UserPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer postId;
    private String userName;
    private String userEmail;

    @Column(length = 1000)
    private String postTitle;

    @Column(length = 4000)
    private String postBody;

    public UserPostEntity() {}

    public UserPostEntity(Integer postId, String userName, String userEmail, String postTitle, String postBody) {
        this.postId = postId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.postTitle = postTitle;
        this.postBody = postBody;
    }

    public Long getId() { return id; }

    public Integer getPostId() { return postId; }
    public void setPostId(Integer postId) { this.postId = postId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getPostTitle() { return postTitle; }
    public void setPostTitle(String postTitle) { this.postTitle = postTitle; }

    public String getPostBody() { return postBody; }
    public void setPostBody(String postBody) { this.postBody = postBody; }



}
