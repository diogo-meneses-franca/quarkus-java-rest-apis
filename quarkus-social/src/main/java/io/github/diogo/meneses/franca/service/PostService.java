package io.github.diogo.meneses.franca.service;

import io.github.diogo.meneses.franca.dto.CreatePostRequest;
import io.github.diogo.meneses.franca.model.Post;
import io.github.diogo.meneses.franca.model.User;
import io.github.diogo.meneses.franca.repository.PostRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

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
		return Response.status(Response.Status.CREATED)
				.entity(post)
				.build();
	}
}
