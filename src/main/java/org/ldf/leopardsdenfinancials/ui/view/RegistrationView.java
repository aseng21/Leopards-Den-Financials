package org.ldf.leopardsdenfinancials.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.wontlost.password.strength.StrengthFeedback;
import com.wontlost.password.strength.VaadinPasswordStrength;
import org.ldf.leopardsdenfinancials.backend.Role;
import org.ldf.leopardsdenfinancials.backend.User;
import org.ldf.leopardsdenfinancials.backend.repository.UserRepository;
import org.ldf.leopardsdenfinancials.backend.service.NotificationService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

/**
 * RegistrationView is a view class representing the sign-up form in the application.
 */
@PageTitle("Sign Up | LDF")
@Route("signup")
@AnonymousAllowed
@Uses(Icon.class)
public class RegistrationView extends VerticalLayout {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TextField usernameField = new TextField("Username");
    private final TextField nameField = new TextField("Name");
    private final PasswordField passwordField = new PasswordField("Password");
    private final PasswordField confirmPasswordField = new PasswordField("Confirm Password");
    private final VaadinPasswordStrength passwordStrength = new VaadinPasswordStrength();

    /**
     * Constructs the RegistrationView with the necessary dependencies.
     *
     * @param userRepository   The repository for managing user data.
     * @param passwordEncoder  The encoder for password hashing.
     */
    public RegistrationView(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        // UI components initialization
        H1 title = new H1("Sign Up Form");
        title.getStyle().setTextAlign(Style.TextAlign.CENTER);
        title.getStyle().set("margin", "10px");
        title.getStyle().set("margin-top", "150px");

        Binder<User> userBinder = new BeanValidationBinder<>(User.class);
        userBinder.forField(usernameField).bind("username");
        userBinder.forField(nameField).bind("name");
        userBinder.forField(passwordField).bind("hashedPassword");
        userBinder.forField(confirmPasswordField).bind("hashedPassword");

        passwordField.setRequiredIndicatorVisible(true);
        passwordField.setPlaceholder("Enter your password");

        confirmPasswordField.setRequiredIndicatorVisible(true);
        confirmPasswordField.setPlaceholder("Confirm your password");

        passwordStrength.getStyle().set("margin-top", "5px");

        FormLayout registrationForm = new FormLayout();

        Button signUpButton = new Button("Sign Up");
        signUpButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        signUpButton.getStyle().set("margin", "15px");

        signUpButton.addClickListener(event -> signUp());

        Div instructions = new Div();
        instructions.setText("Create a new account to access additional features.");

        Div signUpInfo = new Div();
        signUpInfo.setText("Already have an account? ");
        signUpInfo.add(new RouterLink("Log in", LoginView.class));

        registrationForm.add(title, instructions, usernameField, nameField, passwordField,
                confirmPasswordField, passwordStrength, signUpButton, signUpInfo);

        // Configuration and layout setup
        // Restrict maximum width and center on page
        registrationForm.setMaxWidth("500px");
        registrationForm.getStyle().set("margin", "0 auto");

        // These components take full width regardless if we use one column or two (it
        // just looks better that way)
        registrationForm.setColspan(title, 2);
        registrationForm.setColspan(instructions, 2);
        registrationForm.setColspan(passwordStrength, 2);
        registrationForm.setColspan(signUpButton, 2);
        registrationForm.setColspan(signUpInfo, 2);

        // Allow the form layout to be responsive. On device widths 0-490px we have one
        // column, then we have two. Field labels are always on top of the fields.
        registrationForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        add(
                registrationForm
        );

        // Add listener for password field changes
        passwordField.setValueChangeMode(ValueChangeMode.EAGER);
        passwordField.addValueChangeListener(event -> {
            StrengthFeedback strengthFeedback = passwordStrength.getStrengthFeedback(event.getValue());
            int score = Math.max(1, (int) Math.ceil(strengthFeedback.getStrength().getGuessesLog10()));
            passwordStrength.setScore(score);
        });
    }

    /**
     * Handles the sign-up process when the "Sign Up" button is clicked.
     */
    private void signUp() {
        String enteredUsername = usernameField.getValue();
        String enteredName = nameField.getValue();
        String enteredPassword = passwordField.getValue();

        // Validate input
        String enteredConfirmPassword = confirmPasswordField.getValue();
        if (!enteredPassword.equals(enteredConfirmPassword)) {
            Notification.show("Passwords do not match.");
            return;
        }

        if (enteredUsername.isEmpty() || enteredName.isEmpty() || enteredPassword.isEmpty()) {
            Notification.show("Please fill in all fields.");
            return;
        }

        if (enteredPassword.length() < 6 || enteredPassword.length() > 12) {
            Notification.show("Password must be between 6 and 12 characters.");
            return;
        }

        // Check if the username is already taken
        if (userRepository.findByUsername(enteredUsername) != null) {
            NotificationService.createErrorNotification("Username is already taken. Please choose a different one.");
            return;
        }

        // Create a new user
        User newUser = new User();
        newUser.setUsername(enteredUsername);
        newUser.setName(enteredName);
        newUser.setHashedPassword(passwordEncoder.encode(enteredPassword));

        // Set roles for the new user
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER); // You can add more roles as needed
        newUser.setRoles(roles);

        // Save the user to the database
        userRepository.save(newUser);

        Notification.show("User registered successfully!");
    }
}
