package be.belfius.jsondb.repository;

import be.belfius.jsondb.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

}
