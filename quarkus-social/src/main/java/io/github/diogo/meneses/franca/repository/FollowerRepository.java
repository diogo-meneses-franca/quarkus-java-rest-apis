package io.github.diogo.meneses.franca.repository;

import io.github.diogo.meneses.franca.model.Follower;
import io.github.diogo.meneses.franca.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {
	public boolean follows(User user, User follower) {
		var params = Parameters.with("user", user).and("follower", follower).map();
		PanacheQuery<Follower> query = find("user = :user and follower =:follower", params);
		Optional<Follower> result = query.firstResultOptional();
		return result.isPresent();
	}

	public List<Follower> findByUser(Long userId) {
		return find("user.id",userId).list();
	}

	public void deleteByUserAndFollower(User user, User follower) {
		var params = Parameters.with("user", user).and("follower", follower).map();
		delete("user = :user and follower = :follower", params);
	}
}

