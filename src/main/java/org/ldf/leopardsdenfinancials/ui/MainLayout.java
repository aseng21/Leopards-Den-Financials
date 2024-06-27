package org.ldf.leopardsdenfinancials.ui;

import com.vaadin.componentfactory.ToggleButton;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.page.WebStorage;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.ldf.leopardsdenfinancials.backend.Role;
import org.ldf.leopardsdenfinancials.backend.security.AuthenticatedUser;
import org.ldf.leopardsdenfinancials.ui.view.*;
import org.vaadin.lineawesome.LineAwesomeIcon;

import static com.vaadin.flow.component.page.WebStorage.getItem;

/**
 * Main layout for the application.
 */
@PermitAll
@Uses(Icon.class)
public class MainLayout extends AppLayout {

    // Components
    private final H2 viewTitle = new H2();
    private final AuthenticatedUser authenticatedUser;
    private final String js = "document.documentElement.setAttribute('theme', $0)";

    Scroller adminScroller = new Scroller();

    // Constructor
    public MainLayout(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;

        // Configure layout
        setPrimarySection(Section.DRAWER);
        createDrawer();
        createHeader();

        // Usage
        addViewShortcut(HomeView.class, Key.DIGIT_0);
        addViewShortcut(InventoryView.class, Key.DIGIT_1);
        addViewShortcut(BudgetView.class, Key.DIGIT_2);
        addViewShortcut(ReportView.class, Key.DIGIT_3);
    }

    // Header configuration
    private void createHeader() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        // Title configuration
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        // Add components to the header
        addToNavbar(true, toggle, viewTitle);
    }

    // Drawer configuration
    private void createDrawer() {
        // App name and logo
        H1 appName = new H1("Leopard's Den Financials");
        appName.addClassNames(LumoUtility.FontSize.MEDIUM, LumoUtility.Margin.NONE);

        Image daaLogo = new Image("./images/daaLogo.png", "");
        daaLogo.setWidth(80, Unit.PIXELS);
        daaLogo.setHeight(80, Unit.PIXELS);

        Header header = new Header(daaLogo, appName);

        // Navigation and theme toggle
        Scroller userScroller = new Scroller(createNavigation());
        authenticatedUser.get().ifPresent(user -> {
            // Check if the user has the ADMIN role
            if (user.getRoles().contains(Role.ADMIN)) {
                adminScroller = new Scroller(createAdminNavigation());
            }
        });
        ToggleButton themeToggle = new ToggleButton("Theme Switcher");
        themeToggle.setTooltipText("Switch between dark mode and light mode");

        getItem("theme", value -> {
            boolean isDarkTheme = Boolean.parseBoolean(value);
            getElement().executeJs(js, isDarkTheme ? Lumo.DARK : Lumo.LIGHT);

            // Set themeToggle based on the boolean result
            themeToggle.setValue(isDarkTheme);
        });
        HorizontalLayout toggleLayout = new HorizontalLayout(themeToggle);
        toggleLayout.setPadding(true);

        themeToggle.addValueChangeListener(e -> setTheme(e.getValue()));

        // User information and logout
        MenuBar userMenu = new MenuBar();
        userMenu.setThemeName("tertiary-inline contrast");
        userMenu.setOpenOnHover(true);

        // Get the authenticated user
        authenticatedUser.get().ifPresent(user -> {
            Avatar avatar = new Avatar();
            avatar.setName(user.getName());
            userMenu.addItem(avatar);

            // Display user's name and username
            MenuItem userName = userMenu.addItem(user.getName() + " (" + user.getUsername() + ")");
            userName.getSubMenu().addItem("Sign out", e -> authenticatedUser.logout());
            Div div = new Div(new Icon("lumo", "dropdown"));
            userName.add(div);
        });

        // Rest of the code remains the same
        HorizontalLayout logOutLayout = new HorizontalLayout(userMenu);
        logOutLayout.setPadding(true);

        // Add components to the drawer
        addToDrawer(header, userScroller, adminScroller, toggleLayout, logOutLayout);
    }

    // Create navigation items
    private SideNav createNavigation() {
        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Home", HomeView.class, LineAwesomeIcon.HOME_SOLID.create()));
        nav.addItem(new SideNavItem("Inventory", InventoryView.class, LineAwesomeIcon.SHOPPING_CART_SOLID.create()));
        nav.addItem(new SideNavItem("Budget", BudgetView.class, VaadinIcon.CREDIT_CARD.create()));
        nav.addItem(new SideNavItem("Report", ReportView.class, VaadinIcon.NEWSPAPER.create()));

        return nav;
    }

    private SideNav createAdminNavigation() {
        SideNav adminNav = new SideNav();

        adminNav.setLabel("Admin");
        adminNav.setCollapsible(true);

        // Add "User Management" only for users with the ADMIN role
        adminNav.addItem(new SideNavItem("User Management", UserManagementView.class, LineAwesomeIcon.USER.create()));

        return adminNav;
    }

    // Set the view title after navigation
    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    // Get the current page title
    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    // Set the theme based on the toggle value
    private void setTheme(boolean dark) {
        WebStorage.setItem(WebStorage.Storage.LOCAL_STORAGE, "theme", String.valueOf(dark));
        getItem("theme",
                value -> getElement().executeJs(js, Boolean.parseBoolean(value) ? Lumo.DARK : Lumo.LIGHT));
    }

    private void addViewShortcut(Class<? extends Component> viewClass, Key key) {
        UI.getCurrent().addShortcutListener(
                () -> UI.getCurrent().navigate(viewClass),
                key, KeyModifier.ALT
        );
    }
}
