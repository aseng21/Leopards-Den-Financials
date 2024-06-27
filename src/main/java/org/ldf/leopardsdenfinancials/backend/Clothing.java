package org.ldf.leopardsdenfinancials.backend;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing clothing information in the application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Clothing {

    /**
     * Enum representing different categories of clothing.
     */
    public enum ClothingCategory {
        OUTERWEAR, LOWER_BODY, UPPER_BODY, SPORTSWEAR
    }

    /**
     * Primary key for the clothing entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Quantity cannot be empty")
    @Positive(message = "Quantity must be a positive number")
    private Integer quantity;

    @NotNull(message = "Price cannot be empty")
    @Positive(message = "Price must be a positive number")
    private Double price;

    @NotNull(message = "Cost cannot be empty")
    @Positive(message = "Cost must be a positive number")
    private Double cost;

    @NotNull(message = "Profit cannot be empty")
    @Positive(message = "Profit must be a positive number")
    private Double profit;

    @NotNull(message = "Category cannot be empty")
    @Enumerated(EnumType.STRING)
    private ClothingCategory category;
}
