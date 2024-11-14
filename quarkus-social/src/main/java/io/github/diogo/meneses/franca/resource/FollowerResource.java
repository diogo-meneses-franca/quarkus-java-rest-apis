package io.github.diogo.meneses.franca.resource;

import io.github.diogo.meneses.franca.dto.FollowerRequest;
import io.github.diogo.meneses.franca.model.Follower;
import io.github.diogo.meneses.franca.service.FollowerService;
import io.github.diogo.meneses.franca.utils.ValidatorUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

	private final FollowerService service;
	private final ValidatorUtil validator;

	@Inject
	public FollowerResource(FollowerService service, ValidatorUtil validator){
		this.service = service;
		this.validator = validator;
	}

	@POST
	@Path("/{userId}")
	public Response followUser(@PathParam("userId")Long userId, FollowerRequest request){
		Response validation = validator.validate(request);
		if (validation != null) return validation;
		return service.followUser(userId, request);
	}

	@GET
	@Path("/{userId}")
	public Response listFollowers(@PathParam("userId")Long userId){
		return service.listFollowers(userId);
	}

	@DELETE
	@Path("/{userId}")
	public Response unfollow(@PathParam("userId") Long userId, @QueryParam("followerId") Long followerId){
		return service.unfollow(userId, followerId);
	}

}
