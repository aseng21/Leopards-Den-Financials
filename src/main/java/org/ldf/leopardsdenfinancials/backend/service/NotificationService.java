package org.ldf.leopardsdenfinancials.backend.service;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * Utility class for creating custom notifications in the application.
 */
public class NotificationService {

    /**
     * Creates and displays a success notification with the given message.
     *
     * @param message The message to be displayed in the notification.
     */
    public static void createSuccessNotification(String message) {
        // Create success notification with a success theme and 5 seconds duration
        Notification successNotification = new Notification();
        successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        successNotification.setDuration(5000);

        Icon icon = VaadinIcon.CHECK_CIRCLE.create();

        // Create layout with icon, message, and close button
        var layout = new HorizontalLayout(icon, new Text(message), createCloseBtn(successNotification));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        successNotification.add(layout);

        // Display the notification
        successNotification.open();
    }

    /**
     * Creates and displays an error notification with the given message.
     *
     * @param message The message to be displayed in the notification.
     */
    public static void createErrorNotification(String message) {
        // Create error notification with an error theme and 5 seconds duration
        Notification errorNotification = new Notification();
        errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        errorNotification.setDuration(5000);

        Icon icon = VaadinIcon.WARNING.create();

        // Create layout with icon, message, and close button
        var layout = new HorizontalLayout(icon, new Text(message), createCloseBtn(errorNotification));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Display the notification
        errorNotification.add(layout);
        errorNotification.open();
    }

    /**
     * Creates a close button for the given notification.
     *
     * @param notification The notification to be closed.
     * @return The close button.
     */
    private static Button createCloseBtn(Notification notification) {
        // Create close button with close icon and tertiary inline theme
        Button closeBtn = new Button(VaadinIcon.CLOSE_SMALL.create(), clickEvent -> notification.close());
        closeBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        return closeBtn;
    }
}
