package org.springframework.skynet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.skynet.Users;

public interface UsersRepository extends CrudRepository<Users, Long> {
    // repository methods

}
