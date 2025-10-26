package com.UserPost.user_posts;

import com.UserPost.user_posts.dto.MergedPostDto;
import com.UserPost.user_posts.dto.PostDto;
import com.UserPost.user_posts.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserPostsApplicationTests {
	@Test
	public void testMergeAndFilter() {
		UserDto u1 = new UserDto();
		u1.setId(1);
		u1.setName("Alice");
		u1.setEmail("alice@example.com");

		UserDto u2 = new UserDto();
		u2.setId(2);
		u2.setName("Bob");
		u2.setEmail("bob@example.com");

		PostDto p1 = new PostDto();
		p1.setId(11);
		p1.setUserId(1);
		p1.setTitle("short title");
		p1.setBody("body1");

		PostDto p2 = new PostDto();
		p2.setId(12);
		p2.setUserId(2);
		// create a long title > 200
		p2.setTitle("x".repeat(201));
		p2.setBody("body2");

		List<UserDto> users = Arrays.asList(u1, u2);
		List<PostDto> posts = Arrays.asList(p1, p2);

		Map<Integer, UserDto> userById = users.stream()
				.collect(Collectors.toMap(UserDto::getId, u -> u));

		List<MergedPostDto> merged = posts.stream()
				.filter(p -> p.getTitle() != null)
				.filter(p -> p.getTitle().length() <= 200)
				.map(p -> {
					UserDto user = userById.get(p.getUserId());
					String name = user != null ? user.getName() : null;
					String email = user != null ? user.getEmail() : null;
					return new MergedPostDto(p.getId(), name, email, p.getTitle(), p.getBody());
				})
				.collect(Collectors.toList());

		assertEquals(1, merged.size(), "Expected only one post after filtering long title");
		MergedPostDto m = merged.get(0);
		assertEquals(11, m.getPostId());
		assertEquals("Alice", m.getUserName());
		assertEquals("alice@example.com", m.getUserEmail());
		assertEquals("short title", m.getPostTitle());
	}

}
