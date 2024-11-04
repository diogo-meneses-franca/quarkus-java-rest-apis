package io.github.diogo.meneses.franca.service;

import io.github.diogo.meneses.franca.dto.CreateUserRequest;
import io.github.diogo.meneses.franca.model.User;
import io.github.diogo.meneses.franca.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class UserService {

	private final UserRepository repository;

	@Inject
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public User createUser(CreateUserRequest request){
		User user = new User(null, request.getName(), request.getAge());
		repository.persist(user);
		return user;
	}

	public List<User> listAllUsers(){
		return repository.findAll().list();
	}

	@Transactional
	public Response.Status deleteUser(Long userId) {
		User user = repository.findById(userId);
		if (user == null){
			return Response.Status.NOT_FOUND;
		}
		repository.deleteById(userId);
		return Response.Status.NO_CONTENT;
	}

	@Transactional
	public User updateUser(Long userId, CreateUserRequest userData) {

		User user = repository.findById(userId);
		if (user != null){
			user.setName(userData.getName());
			user.setAge(userData.getAge());
			return user;
		}
			return null;
	}


	public User findUser(Long userId) {
		return repository.findById(userId);
	}
}
