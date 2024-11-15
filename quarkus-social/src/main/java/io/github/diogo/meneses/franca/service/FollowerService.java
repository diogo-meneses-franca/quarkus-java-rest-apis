package io.github.diogo.meneses.franca.service;

import io.github.diogo.meneses.franca.dto.FollowerRequest;
import io.github.diogo.meneses.franca.dto.FollowerResponse;
import io.github.diogo.meneses.franca.dto.FollowersPerUserResponse;
import io.github.diogo.meneses.franca.model.Follower;
import io.github.diogo.meneses.franca.model.User;
import io.github.diogo.meneses.franca.repository.FollowerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class FollowerService {

	Logger logger = LoggerFactory.getLogger(FollowerService.class);

	private final FollowerRepository repository;
	private final UserService userService;

	@Inject
	public FollowerService(FollowerRepository repository, UserService userService){
		this.repository = repository;
		this.userService = userService;
	}

	@Transactional
	public Response followUser(Long userId, FollowerRequest request) {
		if(Objects.equals(userId, request.getFollowerId())){
			return Response.status(Response.Status.CONFLICT).build();
		}
		User user = userService.findUser(userId);
		User follower = userService.findUser(request.getFollowerId());
		if (user != null && follower != null){
			boolean follows = repository.follows(user, follower);
			if(!follows){
				Follower newFollower = new Follower(null, user,follower);
				repository.persist(newFollower);
				return Response
						.status(Response.Status.NO_CONTENT)
						.build();
			}
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@Transactional
	public Response listFollowers(Long userId) {
		User user = userService.findUser(userId);
		if(user == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		List<Follower> followers = repository.findByUser(userId);
		List<FollowerResponse> followerResponses = followers.stream().map(FollowerResponse::new).toList();
		FollowersPerUserResponse response = new FollowersPerUserResponse(followerResponses.size(), followerResponses);
		return Response.status(Response.Status.OK).entity(response).build();
	}

	@Transactional
	public Response unfollow(Long userId, Long followerId) {
		User user = userService.findUser(userId);
		User follower = userService.findUser(followerId);
		if(user == null || follower == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		try {
			List<Follower> followerList = repository.findByUser(userId);
			Optional<Follower> filteredFollower = followerList
					.stream()
					.filter(flw -> flw.getFollower()
							.getId()
							.equals(followerId))
					.findFirst();
			if(!filteredFollower.isPresent()){
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			repository.deleteById(filteredFollower.get().getId());
		}catch (RuntimeException e){
			logger.error("FollowerService: unfollow(), error: {}", e.getMessage(), e);
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	public boolean isFollower(Long userId, Long followerId){
		return repository.findByUser(userId)
				.stream()
				.anyMatch(follower -> follower.getFollower().getId().equals(followerId));
	}
}
