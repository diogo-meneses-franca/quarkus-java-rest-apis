package io.github.diogo.meneses.franca.service;

import io.github.diogo.meneses.franca.repository.FollowerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class FollowerService {

	private final FollowerRepository repository;

	@Inject
	public FollowerService(FollowerRepository repository){
		this.repository = repository;
	}

}
