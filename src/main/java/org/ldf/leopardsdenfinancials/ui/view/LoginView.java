package org.ldf.leopardsdenfinancials.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * View representing the login page of the application.
 */
@PageTitle("Login | LDF")
@Route("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    // Login overlay component
    private final LoginOverlay login = new LoginOverlay();

    public LoginView() {
        // Set size and alignment of the layout
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);

        // Customize login form messages
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setForgotPassword("Register");
        i18n.setAdditionalInformation("Contact support@leopardsdenfinancials.com if you're experiencing issues logging into your account");

        // Set title, description, and messages
        login.setTitle("Leopard's Den Financials");
        login.setDescription("");
        login.setI18n(i18n);

        // Open the login overlay by default
        login.setOpened(true);

        // Set action for the login form
        login.setAction("login");

        // Redirect to signup page when "Register" is clicked
        login.addForgotPasswordListener(e -> UI.getCurrent().getPage().setLocation("signup"));

        // Add the login overlay to the layout
        add(login);
    }

    // Method executed before entering the login view
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // Set error state if the URL contains 'error' parameter
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}
