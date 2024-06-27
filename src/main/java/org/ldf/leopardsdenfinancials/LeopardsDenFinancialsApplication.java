package org.ldf.leopardsdenfinancials;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the Leopard's Den Financials application.
 */
@SpringBootApplication
@Theme("ldf-theme")
public class LeopardsDenFinancialsApplication implements AppShellConfigurator {

	/**
	 * The entry point of the application.
	 *
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(LeopardsDenFinancialsApplication.class, args);
	}
}
