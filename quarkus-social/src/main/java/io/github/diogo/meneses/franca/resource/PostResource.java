package io.github.diogo.meneses.franca.resource;

import io.github.diogo.meneses.franca.dto.CreatePostRequest;
import io.github.diogo.meneses.franca.service.PostService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class PostResource {

	private final PostService service;

	@POST
	@Path("/{userId}")
	public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request){
		return service.savePost(userId, request);
	}

	@GET
	@Path("/{userId}")
	public Response listPosts(
			@PathParam("userId") Long userId,
			@HeaderParam("followerId") Long followerId){
		return service.listPosts(userId, followerId);
	}


}
