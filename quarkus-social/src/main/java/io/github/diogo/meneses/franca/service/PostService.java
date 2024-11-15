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
	private final FollowerService followerService;

	@Inject
	public PostService(PostRepository repository, UserService userService, FollowerService followerService) {
		this.repository = repository;
		this.userService = userService;
        this.followerService = followerService;
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

	public Response listPosts(Long userId, Long followerId){
		User user = userService.findUser(userId);
		User follower  = userService.findUser(followerId);
		if(user == null || follower == null) return Response.status(Response.Status.NOT_FOUND).build();
		if (followerService.isFollower(userId, followerId)) {
			List<Post> postList = repository.findPostsByUserSortedByDateTimeDesc(user);
			List<PostResponse> responseList = PostResponse.fromPostList(postList);
			return Response
					.status(Response.Status.OK)
					.entity(responseList)
					.build();
		}
		return Response
				.status(Response.Status.FORBIDDEN)
				.entity("You must be a follower to see this user's posts")
				.build();
	}
}
