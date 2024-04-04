package org.springframework.skynet;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.skynet.Users;

public interface UsersRepository extends CrudRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE u.email = ?1")
    Users findByEmail(String username);

    // repository methods

}
