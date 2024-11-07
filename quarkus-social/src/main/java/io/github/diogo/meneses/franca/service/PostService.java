package io.github.diogo.meneses.franca.service;

import io.github.diogo.meneses.franca.dto.CreatePostRequest;
import io.github.diogo.meneses.franca.dto.PostResponse;
import io.github.diogo.meneses.franca.model.Post;
import io.github.diogo.meneses.franca.model.User;
import io.github.diogo.meneses.franca.repository.PostRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class PostService {

	private final PostRepository repository;
	private final UserService userService;

	@Inject
	public PostService(PostRepository repository, UserService userService) {
		this.repository = repository;
		this.userService = userService;
	}

	@Transactional
	public Response savePost(Long userId, CreatePostRequest request){
		User user = userService.findUser(userId);
		if(user == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		Post post = new Post();
		post.setText(request.getText());
		post.setUser(user);
		repository.persist(post);
		PostResponse response = PostResponse.fromPost(post);
		return Response.status(Response.Status.CREATED)
				.entity(response)
				.build();
	}

	public Response listPosts(Long userId){
		User user = userService.findUser(userId);
		if(user == null) return Response.status(Response.Status.NOT_FOUND).build();
		List<Post> postList = repository.findPostsByUserSortedByDateTimeDesc(user);
		List<PostResponse> responseList = PostResponse.fromPostList(postList);
		return Response
				.status(Response.Status.OK)
				.entity(responseList)
				.build();
	}
}
