package org.ldf.leopardsdenfinancials.ui.view;

import com.flowingcode.vaadin.addons.gridhelpers.GridHelper;
import com.vaadin.componentfactory.EnhancedDateRangePicker;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.ldf.leopardsdenfinancials.backend.Clothing;
import org.ldf.leopardsdenfinancials.backend.Transaction;
import org.ldf.leopardsdenfinancials.backend.service.ClothingService;
import org.ldf.leopardsdenfinancials.backend.service.TransactionService;
import org.ldf.leopardsdenfinancials.ui.MainLayout;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;
import software.xdev.vaadin.grid_exporter.GridExporter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * View representing the budget section of the application.
 */
@PageTitle("Budget | LDF")
@Route(value = "budget", layout = MainLayout.class)
@PermitAll
public class BudgetView extends VerticalLayout {

    /**
     * Constructs the BudgetView.
     *
     * @param service    Transaction service for managing transactions.
     * @param serviceTwo Clothing service for managing clothing.
     */
    public BudgetView(TransactionService service, ClothingService serviceTwo) {

        // Crud instance
        GridCrud<Transaction> crud = new GridCrud<>(Transaction.class, new HorizontalSplitCrudLayout());
        crud.setUpdateOperationVisible(false);
        crud.setClickRowToUpdate(true);
        crud.setShowNotifications(false);
        crud.getGrid().addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        // Shortcuts
        UI.getCurrent().addShortcutListener(
                () -> crud.getAddButton().click(), Key.KEY_A, KeyModifier.ALT
        );

        UI.getCurrent().addShortcutListener(
                () -> crud.getDeleteButton().click(), Key.KEY_D, KeyModifier.ALT
        );

        // Additional components
        DatePicker filter = new DatePicker();
        filter.setLabel("Filter by date");
        filter.setClearButtonVisible(true);
        filter.setPrefixComponent(VaadinIcon.SEARCH.create());
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.ALT);

        EnhancedDateRangePicker filterBetweenDates = new EnhancedDateRangePicker();
        filterBetweenDates.setClearButtonVisible(true);
        filterBetweenDates.setPrefixComponent(VaadinIcon.SEARCH.create());
        filterBetweenDates.setLabel("Filter between dates");

        // Context menu for grid
        GridContextMenu<Transaction> menu = crud.getGrid().addContextMenu();
        menu.addItem("Add", event -> crud.getAddButton().click());
        menu.addItem("Delete", event -> crud.getDeleteButton().click());
        menu.add(new Hr());
        menu.addItem("Refresh", event -> crud.getFindAllButton().click());

        // Grid configuration
        crud.getGrid().setColumns("clothingName", "quantity", "profit", "date");
        crud.getGrid().setColumnReorderingAllowed(true);

        // Set up column toggle visibility based on the initial state
        GridHelper.setColumnToggleVisible(crud.getGrid(), true);

        // Set up column toggle captions
        GridHelper.setHidingToggleCaption(crud.getGrid().getColumnByKey("clothingName"), "Clothing Name");
        GridHelper.setHidingToggleCaption(crud.getGrid().getColumnByKey("quantity"), "Quantity");
        GridHelper.setHidingToggleCaption(crud.getGrid().getColumnByKey("profit"), "Profit");
        GridHelper.setHidingToggleCaption(crud.getGrid().getColumnByKey("date"), "Date");

        // Form configuration
        crud.getCrudFormFactory().setVisibleProperties("clothing", "quantity", "date");
        crud.getCrudFormFactory().setUseBeanValidation(true);

        crud.getCrudFormFactory().setFieldProvider("clothing",
                new ComboBoxProvider<>("Clothing", serviceTwo.findAll(),
                        new TextRenderer<>(Clothing::getName), Clothing::getName));

        // Format the "date" column
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        crud.getGrid().getColumnByKey("date")
                .setHeader("Date")
                .setRenderer(new TextRenderer<>(transaction -> transaction.getDate().format(dateFormatter)));

        // Export configuration
        Button exportBtn = new Button("Export", new Icon(VaadinIcon.UPLOAD));
        exportBtn.addClickListener(e -> GridExporter
                .newWithDefaults(crud.getGrid())
                .open());

        // Layout configuration
        add(
                new H1("Budget"),
                new HorizontalLayout(filter, filterBetweenDates),
                crud,
                exportBtn
        );

        crud.setOperations(
                () -> {
                    LocalDate startDate = filterBetweenDates.getValue().getStartDate();
                    LocalDate endDate = filterBetweenDates.getValue().                    getEndDate();

                    if (!filter.isEmpty() && startDate != null && endDate != null) {
                        // Both filters are set
                        return service.findAllTransactionsByDate(startDate, endDate)
                                .stream()
                                .filter(transaction -> transaction.getDate().equals(filter.getValue()))
                                .collect(Collectors.toList());
                    } else if (!filter.isEmpty()) {
                        // Only date filter is set
                        return service.findByDate(filter.getValue());
                    } else if (startDate != null && endDate != null) {
                        // Only date range filter is set
                        return service.findAllTransactionsByDate(startDate, endDate);
                    } else {
                        // No filters are set
                        return service.findAll();
                    }
                },
                service::add,
                service::update,
                service::delete
        );

        filter.addValueChangeListener(e -> crud.refreshGrid());
        filterBetweenDates.addValueChangeListener(e -> crud.refreshGrid());
    }
}

