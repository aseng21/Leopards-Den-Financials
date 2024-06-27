package org.ldf.leopardsdenfinancials.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

/**
 * Entity class representing a user in the application.
 */
@Entity
@Data
@Table(name = "application_user")
public class User {

    /**
     * Primary key for the user entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username for the user, must be alphanumeric.
     */
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must be alphanumeric")
    private String username;

    /**
     * Full name of the user.
     */
    @NotBlank(message = "Name cannot be blank")
    private String name;

    /**
     * Hashed password of the user (JsonIgnore to prevent it from being serialized in JSON).
     */
    @JsonIgnore
    @NotBlank(message = "Password cannot be blank")
    private String hashedPassword;

    /**
     * Set of roles associated with the user.
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
