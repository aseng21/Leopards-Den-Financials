package org.ldf.leopardsdenfinancials.backend.service;

import lombok.RequiredArgsConstructor;
import org.ldf.leopardsdenfinancials.backend.Transaction;
import org.ldf.leopardsdenfinancials.backend.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for managing transactions.
 */
@Service
@RequiredArgsConstructor
public class TransactionService implements CrudListener<Transaction> {

    private final TransactionRepository repository;

    /**
     * Retrieve all transactions.
     *
     * @return List of all transactions.
     */
    @Override
    public List<Transaction> findAll() {
        return repository.findAll();
    }

    /**
     * Add a new transaction.
     *
     * @param transaction The transaction to be added.
     * @return The added transaction.
     */
    @Override
    public Transaction add(Transaction transaction) {
        try {
            // Extract clothing details
            var clothing = transaction.getClothing();

            // Set clothing name and calculate profit
            transaction.setClothingName(clothing.getName());
            transaction.setProfit((transaction.getQuantity()) * (clothing.getPrice() - clothing.getCost()));

            // Show success notification
            NotificationService.createSuccessNotification("Transaction of " + transaction.getQuantity() + " " + transaction.getClothingName() + "s added on " + transaction.getDate() + "!");

            // Save and return the added transaction
            return repository.save(transaction);
        } catch (Exception e) {
            // Show error notification if adding fails
            NotificationService.createErrorNotification("Failed to add Transaction!");
            return null;
        }
    }

    /**
     * Update an existing transaction.
     *
     * @param transaction The transaction to be updated.
     * @return The updated transaction.
     */
    @Override
    public Transaction update(Transaction transaction) {
        try {
            // Extract clothing details
            var clothing = transaction.getClothing();

            // Set clothing name and calculate profit
            transaction.setClothingName(clothing.getName());
            transaction.setProfit((transaction.getQuantity()) * (clothing.getPrice() - clothing.getCost()));

            // Show success notification
            NotificationService.createSuccessNotification("Transaction updated!");

            // Save and return the updated transaction
            return repository.save(transaction);
        } catch (Exception e) {
            // Show error notification if updating fails
            NotificationService.createErrorNotification("Failed to update Transaction!");
            return null;
        }
    }

    /**
     * Delete a transaction.
     *
     * @param transaction The transaction to be deleted.
     */
    @Override
    public void delete(Transaction transaction) {
        repository.delete(transaction);
    }

    /**
     * Find transactions by date.
     *
     * @param date The date to search for transactions.
     * @return List of transactions matching the date.
     */
    public List<Transaction> findByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    /**
     * Find total profit within a date range.
     *
     * @param startDate The start date of the date range.
     * @param endDate   The end date of the date range.
     * @return Total profit within the specified date range.
     */
    public Double findProfitByDate(LocalDate startDate, LocalDate endDate) {
        return repository.sumProfitByDateBetween(startDate, endDate);
    }

    /**
     * Find total quantity within a date range.
     *
     * @param startDate The start date of the date range.
     * @param endDate   The end date of the date range.
     * @return Total quantity within the specified date range.
     */
    public Integer findQuantityByDate(LocalDate startDate, LocalDate endDate) {
        return repository.sumQuantityByDateBetween(startDate, endDate);
    }

    /**
     * Find the most popular item in the last 7 days.
     *
     * @param startDate The start date of the date range (7 days ago).
     * @param endDate   The end date of the date range (today).
     * @return The most popular item within the specified date range.
     */
    public String findMostPopularItemLast7Days(LocalDate startDate, LocalDate endDate) {
        return repository.findMostPopularItemLast7Days(startDate, endDate);
    }

    /**
     * Find all transactions within a date range.
     *
     * @param startDate The start date of the date range.
     * @param endDate   The end date of the date range.
     * @return List of transactions within the specified date range.
     */
    public List<Transaction> findAllTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        return repository.findAllTransactionsInPastWeek(startDate, endDate);
    }
}
