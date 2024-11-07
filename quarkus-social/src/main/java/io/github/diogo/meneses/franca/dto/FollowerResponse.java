package io.github.diogo.meneses.franca.dto;

import io.github.diogo.meneses.franca.model.Follower;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowerResponse {

	private Long followId;
	private String followerName;

	public FollowerResponse(Follower follower) {
		this(follower.getId(), follower.getFollower().getName());
	}
}
