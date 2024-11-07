package io.github.diogo.meneses.franca.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FollowersPerUserResponse {

	private Integer followerQuantity;
	private List<FollowerResponse> followers;
}
