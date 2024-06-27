package org.ldf.leopardsdenfinancials.backend.repository;

import org.ldf.leopardsdenfinancials.backend.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing Transaction entities.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Find transactions by date.
     *
     * @param date The date to filter transactions.
     * @return List of transactions on the specified date.
     */
    List<Transaction> findByDate(LocalDate date);

    /**
     * Calculate the sum of transaction profits within a date range.
     *
     * @param startDate The start date of the date range.
     * @param endDate   The end date of the date range.
     * @return The sum of transaction profits within the specified date range.
     */
    @Query("SELECT COALESCE(SUM(t.profit), 0) FROM Transaction t WHERE t.date BETWEEN :startDate AND :endDate")
    Double sumProfitByDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Calculate the sum of quantities of transactions within a date range.
     *
     * @param startDate The start date of the date range.
     * @param endDate   The end date of the date range.
     * @return The sum of quantities of transactions within the specified date range.
     */
    @Query("SELECT COALESCE(SUM(t.quantity), 0) FROM Transaction t WHERE t.date BETWEEN :startDate AND :endDate")
    Integer sumQuantityByDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find the most popular item sold in the last 7 days.
     *
     * @param startDate The start date of the 7-day range.
     * @param endDate   The end date of the 7-day range.
     * @return A string representation of the most popular item sold in the last 7 days.
     */
    @Query("SELECT CONCAT(t.clothingName, ', with ', SUM(t.quantity), ' sold') " +
            "FROM Transaction t " +
            "WHERE t.date BETWEEN :startDate AND :endDate " +
            "GROUP BY t.clothingName " +
            "ORDER BY SUM(t.quantity) DESC " +
            "LIMIT 1")
    String findMostPopularItemLast7Days(LocalDate startDate, LocalDate endDate);

    /**
     * Find all transactions within the past week.
     *
     * @param startDate The start date of the past week.
     * @param endDate   The end date of the past week.
     * @return List of transactions within the past week.
     */
    @Query("SELECT t FROM Transaction t " +
            "WHERE t.date BETWEEN :startDate AND :endDate")
    List<Transaction> findAllTransactionsInPastWeek(LocalDate startDate, LocalDate endDate);
}
