package org.ldf.leopardsdenfinancials.backend.service;

import lombok.RequiredArgsConstructor;
import org.ldf.leopardsdenfinancials.backend.Clothing;
import org.ldf.leopardsdenfinancials.backend.repository.ClothingRepository;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.List;

/**
 * Service class for managing clothing entities.
 */
@Service
@RequiredArgsConstructor
public class ClothingService implements CrudListener<Clothing> {

    private final ClothingRepository repository;

    /**
     * Retrieve all clothing entities.
     *
     * @return List of all clothing entities.
     */
    @Override
    public List<Clothing> findAll() {
        return repository.findAll();
    }

    /**
     * Add a new clothing entity.
     *
     * @param clothing The clothing entity to be added.
     * @return The added clothing entity.
     */
    @Override
    public Clothing add(Clothing clothing) {
        try {
            // Calculate and set profit
            clothing.setProfit((clothing.getQuantity()) * (clothing.getPrice() - clothing.getCost()));

            // Show success notification
            NotificationService.createSuccessNotification("Clothing added!");

            // Save and return the added clothing entity
            return repository.save(clothing);

        } catch (Exception e) {
            // Show error notification if adding fails
            NotificationService.createErrorNotification("Failed to add Clothing!");
            return null;
        }
    }

    /**
     * Add a new clothing entity in a specific category.
     *
     * @param clothing The clothing entity to be added.
     * @param category The category of the clothing.
     * @return The added clothing entity.
     */
    public Clothing addInCategory(Clothing clothing, Clothing.ClothingCategory category) {
        try {
            // Calculate and set profit
            clothing.setProfit((clothing.getQuantity()) * (clothing.getPrice() - clothing.getCost()));

            // Set category
            clothing.setCategory(category);

            // Show success notification
            NotificationService.createSuccessNotification("Clothing added!");

            // Save and return the added clothing entity
            return repository.save(clothing);
        } catch (Exception e) {
            // Show error notification if adding fails
            NotificationService.createErrorNotification("Failed to add Clothing!");
            return null;
        }
    }

    /**
     * Update an existing clothing entity.
     *
     * @param clothing The clothing entity to be updated.
     * @return The updated clothing entity.
     */
    @Override
    public Clothing update(Clothing clothing) {
        try {
            // Calculate and set profit
            clothing.setProfit((clothing.getQuantity()) * (clothing.getPrice() - clothing.getCost()));

            // Show success notification
            NotificationService.createSuccessNotification("Clothing updated!");

            // Save and return the updated clothing entity
            return repository.save(clothing);
        } catch (Exception e) {
            // Show error notification if updating fails
            NotificationService.createErrorNotification("Failed to update Clothing!");
            return null;
        }
    }

    /**
     * Update an existing clothing entity in a specific category.
     *
     * @param clothing The clothing entity to be updated.
     * @return The updated clothing entity.
     */
    public Clothing updateInCategory(Clothing clothing) {
        try {
            // Calculate and set profit
            clothing.setProfit((clothing.getQuantity()) * (clothing.getPrice() - clothing.getCost()));

            // Show success notification
            NotificationService.createSuccessNotification("Clothing updated!");

            // Save and return the updated clothing entity
            return repository.save(clothing);
        } catch (Exception e) {
            // Show error notification if updating fails
            NotificationService.createErrorNotification("Failed to update Clothing!");
            return null;
        }
    }

    /**
     * Delete a clothing entity.
     *
     * @param clothing The clothing entity to be deleted.
     */
    @Override
    public void delete(Clothing clothing) {
        repository.delete(clothing);
    }

    /**
     * Find clothing entities by name.
     *
     * @param name The name to search for.
     * @return List of clothing entities matching the name.
     */
    public List<Clothing> findByName(String name) {
        // If no matches found, return all clothing entities
        if (repository.findByName(name).isEmpty())
            return repository.findAll();
        // Otherwise, return matching entities
        return repository.findByName(name);
    }

    /**
     * Find clothing entities by name and category.
     *
     * @param name     The name to search for.
     * @param category The category to filter by.
     * @return List of clothing entities matching the name and category.
     */
    public List<Clothing> findByNameAndCategory(String name, Clothing.ClothingCategory category) {
        if (name == null || name.trim().isEmpty()) {
            // If no name provided, return by category only
            return repository.findByCategory(category);
        } else {
            // Otherwise, return by name and category
            return repository.findByNameAndCategory(name, category);
        }
    }

    /**
     * Get the count of all clothing entities.
     *
     * @return The count of all clothing entities.
     */
    public long count() {
        return repository.count();
    }
}
