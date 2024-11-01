package io.github.diogo.meneses.franca.repository;

import io.github.diogo.meneses.franca.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

//Cria uma instancia dessa classe dentro da aplicação para que ela possa ser injetada
@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}
