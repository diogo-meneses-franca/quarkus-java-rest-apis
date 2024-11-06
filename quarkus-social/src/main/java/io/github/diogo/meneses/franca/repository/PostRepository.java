package io.github.diogo.meneses.franca.repository;

import io.github.diogo.meneses.franca.model.Post;
import io.github.diogo.meneses.franca.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {

	public List<Post> findPostsByUserSortedByDateTimeDesc(User user) {
		return find("user", Sort.by("dateTime", Sort.Direction.Descending), user).list();
	}
}
