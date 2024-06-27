package org.ldf.leopardsdenfinancials.backend.security;

import java.util.Optional;

import com.vaadin.flow.spring.security.AuthenticationContext;
import org.ldf.leopardsdenfinancials.backend.User;
import org.ldf.leopardsdenfinancials.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Helper class for retrieving and managing the authenticated user.
 */
@Component
public class AuthenticatedUser {

    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;

    // Constructor to inject dependencies
    public AuthenticatedUser(AuthenticationContext authenticationContext, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.authenticationContext = authenticationContext;
    }

    /**
     * Retrieve the currently authenticated user from the authentication context.
     *
     * @return An Optional containing the authenticated user, if present.
     */
    @Transactional
    public Optional<User> get() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .map(userDetails -> userRepository.findByUsername(userDetails.getUsername()));
    }

    /**
     * Log out the currently authenticated user.
     */
    public void logout() {
        authenticationContext.logout();
    }
}
