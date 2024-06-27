package org.ldf.leopardsdenfinancials.ui.view;

import com.flowingcode.vaadin.addons.gridhelpers.GridHelper;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.apache.commons.lang3.StringUtils;
import org.ldf.leopardsdenfinancials.backend.Clothing;
import org.ldf.leopardsdenfinancials.backend.service.ClothingService;
import org.ldf.leopardsdenfinancials.ui.MainLayout;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;
import software.xdev.vaadin.grid_exporter.GridExporter;

import java.util.Arrays;
import java.util.List;

@PageTitle("Inventory | LDF")
@Route(value = "inventory", layout = MainLayout.class)
@PermitAll
public class InventoryView extends HorizontalLayout {

    public InventoryView(ClothingService service) {
        TabSheet tabSheet = new TabSheet();
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_CENTERED);
        tabSheet.setSizeFull();

        // Create a VerticalLayout to hold the components for "All" category
        VerticalLayout allLayout = createCrudLayout(service, null);
        tabSheet.add("All", allLayout);

        // Iterate over clothing categories and create corresponding tabs
        for (Clothing.ClothingCategory category : Clothing.ClothingCategory.values()) {
            VerticalLayout categoryLayout = createCrudLayout(service, category);
            tabSheet.add(StringUtils.capitalize(category.name().toLowerCase().replace("_", " ")), categoryLayout);
        }

        // Layout configuration
        add(tabSheet);
    }

    private VerticalLayout createCrudLayout(ClothingService service, Clothing.ClothingCategory category) {
        GridCrud<Clothing> crud = new GridCrud<>(Clothing.class, new HorizontalSplitCrudLayout());
        crud.setUpdateOperationVisible(false);
        crud.setClickRowToUpdate(true);
        crud.setShowNotifications(false);
        crud.getGrid().addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        TextField filter = createFilterTextField(crud);

        // Grid configuration
        configureGrid(crud, category);

        // Shortcuts
        configureShortcuts(crud);

        // Context menu for grid
        configureContextMenu(crud);

        // Form configuration
        configureForm(crud, category);

        // Export configuration
        Button exportBtn = createExportButton(crud);

        // Operations
        configureOperations(service, crud, filter, category);

        return new VerticalLayout(filter, crud, exportBtn);
    }

    private TextField createFilterTextField(GridCrud<Clothing> crud) {
        TextField filter = new TextField();
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.setPlaceholder("Filter by name");
        filter.setClearButtonVisible(true);
        filter.setPrefixComponent(VaadinIcon.SEARCH.create());
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.ALT);
        filter.addValueChangeListener(e -> crud.refreshGrid());
        return filter;
    }

    private void configureGrid(GridCrud<Clothing> crud, Clothing.ClothingCategory category) {
        if (category == null) {
            crud.getGrid().setColumns("name", "quantity", "cost", "price", "profit");

            // Add a custom renderer for the category column to capitalize and format the text
            crud.getGrid().addColumn(new TextRenderer<>(item -> StringUtils.capitalize(item.getCategory().name().toLowerCase().replace("_", " "))))
                    .setHeader("Category").setKey("category");
        } else {
            crud.getGrid().setColumns("name", "quantity", "cost", "price", "profit");
        }

        // Set up column toggle columns
        GridHelper.setHidingToggleCaption(crud.getGrid().getColumnByKey("name"), "Clothing Name");
        GridHelper.setHidingToggleCaption(crud.getGrid().getColumnByKey("quantity"), "Quantity");
        GridHelper.setHidingToggleCaption(crud.getGrid().getColumnByKey("cost"), "Cost");
        GridHelper.setHidingToggleCaption(crud.getGrid().getColumnByKey("price"), "Price");
        GridHelper.setHidingToggleCaption(crud.getGrid().getColumnByKey("profit"), "Profit");

        if (category == null) {
            Grid.Column<Clothing> categoryColumn = crud.getGrid().getColumnByKey("category");
            GridHelper.setHidingToggleCaption(categoryColumn, "Category");
        }

        // Set up column toggle visibility based on the initial state
        GridHelper.setColumnToggleVisible(crud.getGrid(), true);

        crud.getGrid().getColumns().getFirst().setFooter("Average: ");
        crud.getGrid().setColumnReorderingAllowed(true);
    }

    private void configureShortcuts(GridCrud<Clothing> crud) {
        UI.getCurrent().addShortcutListener(
                () -> crud.getAddButton().click(), Key.KEY_A, KeyModifier.ALT
        );

        UI.getCurrent().addShortcutListener(
                () -> crud.getDeleteButton().click(), Key.KEY_D, KeyModifier.ALT
        );
    }

    private void configureContextMenu(GridCrud<Clothing> crud) {
        GridContextMenu<Clothing> menu = crud.getGrid().addContextMenu();
        menu.addItem("Add", event -> crud.getAddButton().click());
        menu.addItem("Delete", event -> crud.getDeleteButton().click());
        menu.add(new Hr());
        menu.addItem("Refresh", event -> crud.getFindAllButton().click());
    }

    private void configureForm(GridCrud<Clothing> crud, Clothing.ClothingCategory category) {
        if (category != null) {
            crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD,"name", "quantity", "cost", "price");
            crud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "name", "quantity", "cost", "price", "category");
            crud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "name", "quantity");
        } else {
            crud.getCrudFormFactory().setVisibleProperties("name", "quantity", "cost", "price", "category");
        }

        crud.getCrudFormFactory().setFieldProvider("category",
                new ComboBoxProvider<>("Category", Arrays.asList(Clothing.ClothingCategory.values()),
                        new TextRenderer<>(c -> StringUtils.capitalize(c.name().toLowerCase().replace("_", " "))),
                        Clothing.ClothingCategory::name));

        crud.getCrudFormFactory().setUseBeanValidation(true);
    }

    private Button createExportButton(GridCrud<Clothing> crud) {
        Button exportBtn = new Button("Export", new Icon(VaadinIcon.UPLOAD));
        exportBtn.addClickListener(e -> GridExporter
                .newWithDefaults(crud.getGrid())
                .open());
        return exportBtn;
    }

    private void configureOperations(ClothingService service, GridCrud<Clothing> crud, TextField filter, Clothing.ClothingCategory category) {
        crud.setOperations(
                () -> {
                    updateGridFooter(service, crud, category, filter.getValue());
                    return (category != null)
                            ? service.findByNameAndCategory(filter.getValue(), category)
                            : service.findByName(filter.getValue());
                },
                item -> {
                    updateGridFooter(service, crud, category, filter.getValue());
                    return (category != null)
                            ? service.addInCategory(item, category)
                            : service.add(item);
                },
                item -> {
                    updateGridFooter(service, crud, category, filter.getValue());
                    return (category != null)
                            ? service.updateInCategory(item)
                            : service.update(item);
                },
                item -> {
                    updateGridFooter(service, crud, category, filter.getValue());
                    service.delete(item);
                }
        );
    }

    private void updateGridFooter(ClothingService service, GridCrud<Clothing> crud,
                                  Clothing.ClothingCategory category, String filterValue) {
        List<Clothing> filteredItems = (category != null)
                ? service.findByNameAndCategory(filterValue, category)
                : service.findByName(filterValue);

        double quantityFooter = filteredItems.stream().mapToDouble(Clothing::getQuantity).average().orElse(0.0);
        double costFooter = filteredItems.stream().mapToDouble(Clothing::getCost).average().orElse(0.0);
        double priceFooter = filteredItems.stream().mapToDouble(Clothing::getPrice).average().orElse(0.0);
        double profitFooter = filteredItems.stream().mapToDouble(Clothing::getProfit).average().orElse(0.0);

        crud.getGrid().getColumns().get(1).setFooter(String.valueOf((int) quantityFooter));
        crud.getGrid().getColumns().get(2).setFooter(String.valueOf((int) costFooter));
        crud.getGrid().getColumns().get(3).setFooter(String.valueOf((int) priceFooter));
        crud.getGrid().getColumns().get(4).setFooter(String.valueOf((int) profitFooter));
    }
}

