package io.github.diogo.meneses.franca.resource;

import io.github.diogo.meneses.franca.dto.CreateUserRequest;
import io.github.diogo.meneses.franca.dto.ResponseError;
import io.github.diogo.meneses.franca.model.User;
import io.github.diogo.meneses.franca.service.UserService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;

@Path(("/users"))
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private final UserService userService;
	private final Validator validator;

	@Inject
	public UserResource(UserService userService, Validator validator) {
		this.userService = userService;
		this.validator = validator;
	}

	@POST
	public Response createUser(CreateUserRequest userRequest) {
		Response validation = validate(userRequest);
		if (validation != null){
			return validation;
		}
		User user = userService.createUser(userRequest);
		return Response.ok(user).build();
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
		Response.Status status = userService.updateUser(userId, user);
		return Response.status(status).build();
	}

	private Response validate(CreateUserRequest request){
		Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
		if(!violations.isEmpty()){
			ResponseError responseError = ResponseError.createFromValidation(violations);
			return Response.status(400).entity(responseError).build();
		}
		return null;
	}


}
