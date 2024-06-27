package org.ldf.leopardsdenfinancials.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.ldf.leopardsdenfinancials.backend.Role;
import org.ldf.leopardsdenfinancials.backend.User;
import org.ldf.leopardsdenfinancials.backend.repository.UserRepository;
import org.ldf.leopardsdenfinancials.ui.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * View for managing users with admin privileges.
 */
@PageTitle("User Management | LDF")
@Route(value = "admin", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class UserManagementView extends VerticalLayout {

    private final UserRepository userRepository;
    private final Grid<User> userGrid;
    private Button promoteButton;

    /**
     * Constructs the UserManagementView.
     *
     * @param userRepository The repository for managing user data.
     */
    @Autowired
    public UserManagementView(UserRepository userRepository) {
        this.userRepository = userRepository;

        // Create and configure the grid
        userGrid = new Grid<>(User.class);
        userGrid.setColumns("username", "name", "roles");

        // Add a button column for promoting users to admin
        userGrid.addComponentColumn(user -> {
            promoteButton = new Button("Promote to Admin", event -> promoteToAdmin(user));
            promoteButton.setEnabled(!user.getRoles().contains(Role.ADMIN));
            return promoteButton;
        }).setHeader("Actions");

        GridContextMenu<User> menu = userGrid.addContextMenu();
        menu.addItem("Promote Newest User to Admin", event -> promoteButton.click());

        // Set up ListDataProvider for the grid
        ListDataProvider<User> dataProvider = new ListDataProvider<>(userRepository.findAll());
        userGrid.setDataProvider(dataProvider);

        // Add the grid to the layout
        add(userGrid);
    }

    /**
     * Promotes a user to admin role.
     *
     * @param user The user to be promoted.
     */
    private void promoteToAdmin(User user) {
        user.getRoles().add(Role.ADMIN);
        userRepository.save(user);

        // Refresh the data provider to update the grid
        userGrid.getDataProvider().refreshAll();

        Notification.show(user.getName() + " (" + user.getUsername() + ") promoted to Admin");
    }
}