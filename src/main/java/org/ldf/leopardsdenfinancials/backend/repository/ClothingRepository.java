package org.ldf.leopardsdenfinancials.backend.repository;

import org.ldf.leopardsdenfinancials.backend.Clothing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Clothing entities.
 */
@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Long> {

    /**
     * Custom query to find clothing by name using a case-insensitive LIKE search.
     *
     * @param name The name to search for (case-insensitive).
     * @return List of clothing matching the specified name.
     */
    @Query("SELECT c FROM Clothing c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Clothing> findByName(@Param("name") String name);

    /**
     * Custom query to find clothing by name and category using a case-insensitive LIKE search.
     *
     * @param name     The name to search for (case-insensitive).
     * @param category The category to filter by.
     * @return List of clothing matching the specified name and category.
     */
    @Query("SELECT c FROM Clothing c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')) AND c.category = :category")
    List<Clothing> findByNameAndCategory(@Param("name") String name, @Param("category") Clothing.ClothingCategory category);

    /**
     * Default method to find clothing by category.
     *
     * @param category The category to filter by.
     * @return List of clothing matching the specified category.
     */
    List<Clothing> findByCategory(Clothing.ClothingCategory category);
}
