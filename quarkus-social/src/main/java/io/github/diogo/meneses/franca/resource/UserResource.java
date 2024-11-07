package io.github.diogo.meneses.franca.resource;

import io.github.diogo.meneses.franca.dto.CreateUserRequest;
import io.github.diogo.meneses.franca.model.User;
import io.github.diogo.meneses.franca.service.UserService;
import io.github.diogo.meneses.franca.utils.ValidatorUtil;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path(("/users"))
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private final UserService userService;
	private final ValidatorUtil validator;

	@Inject
	public UserResource(UserService userService, ValidatorUtil validator) {
		this.userService = userService;
		this.validator = validator;
	}

	@POST
	public Response createUser(CreateUserRequest userRequest) {
		Response validation = validator.validate(userRequest);
		if (validation != null){
			return validation;
		}
		User user = userService.createUser(userRequest);
		return Response.status(Response.Status.CREATED)
				.entity(user)
				.build();
	}

	@GET
	public Response listAllUsers(){
		List<User> users = userService.listAllUsers();
		return Response.ok(users).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteUser(@PathParam("id") Long userId){
		Response.Status status = userService.deleteUser(userId);
		return Response.status(status).build();
	}

	@PUT
	@Path("/{id}")
	public Response updateUser(@PathParam("id")Long userId, CreateUserRequest user){
		User updatedUser = userService.updateUser(userId, user);
		if (updatedUser != null) return Response.ok(updatedUser).build();
		return Response.status(Response.Status.NOT_FOUND).build();
	}

}
