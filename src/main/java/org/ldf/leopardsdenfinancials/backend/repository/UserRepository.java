package org.ldf.leopardsdenfinancials.backend.repository;

import org.ldf.leopardsdenfinancials.backend.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository interface for managing User entities.
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Find a user by their username.
     *
     * @param username The username of the user to be found.
     * @return The user with the specified username.
     */
    User findByUsername(String username);
}
