package org.ldf.leopardsdenfinancials.ui.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.wontlost.password.strength.VaadinPasswordStrength;
import jakarta.annotation.security.PermitAll;
import org.ldf.leopardsdenfinancials.ui.MainLayout;
import software.xdev.vaadin.editable_label.predefined.EditableLabelTextArea;
import software.xdev.vaadin.editable_label.predefined.EditableLabelTextField;

/**
 * View representing the home page of the application.
 */
@PageTitle("Home | LDF")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {

    /**
     * Constructs the HomeView.
     */
    public HomeView() {

        // Create an image for the DAA uniforms
        Image daaUniforms = new Image("./images/daaUniforms.png", "");
        daaUniforms.setWidth(800, Unit.PIXELS);
        daaUniforms.setHeight(500, Unit.PIXELS);

        // Create a VerticalLayout to hold the image and text
        VerticalLayout layout = new VerticalLayout(
                daaUniforms,
                new EditableLabelTextArea().withValue(
                        "The Leopard’s Den is the school's spirit shop that sells uniforms and gear, " +
                                "along with house color shirts and drama shirts! " +
                                "The Leopard's Den is open before and after-school except Tuesdays. The " +
                                "Leopard’s Den is located in the hallway just off of the atrium leading " +
                                "to the main sports hall."
                )
        );

        // Center items vertically in the VerticalLayout
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Add the layout to the HomeView
        add(layout);
    }
}
