package io.github.diogo.meneses.franca.dto;

import io.github.diogo.meneses.franca.model.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponse {

	private String text;
	private LocalDateTime dateTime;

	public static List<PostResponse> fromPostList(List<Post> posts) {
		return posts.stream().map(PostResponse::fromPost).toList();
	}

	public static PostResponse fromPost(Post post) {
		PostResponse response = new PostResponse();
		response.text = post.getText();
		response.dateTime = post.getDateTime();
		return response;
	}
}
