package org.ldf.leopardsdenfinancials.backend.security;

import java.util.List;
import java.util.stream.Collectors;

import org.ldf.leopardsdenfinancials.backend.User;
import org.ldf.leopardsdenfinancials.backend.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for Spring Security's UserDetailsService.
 * This class is responsible for loading user details from the database.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor to inject the UserRepository dependency.
     *
     * @param userRepository The UserRepository instance.
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Load user details by username.
     *
     * @param username The username to load user details for.
     * @return UserDetails object representing the user.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        // Throw an exception if the user is not found
        if (user == null)
            throw new UsernameNotFoundException("No user present with username: " + username);
        else
            // Create a UserDetails object based on the retrieved user
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getHashedPassword(),
                    getAuthorities(user));
    }

    /**
     * Get authorities (roles) for a given user.
     *
     * @param user The user for whom authorities are retrieved.
     * @return List of GrantedAuthority objects.
     */
    private static List<GrantedAuthority> getAuthorities(User user) {
        // Map user roles to Spring Security GrantedAuthority objects
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}
