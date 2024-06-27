package org.ldf.leopardsdenfinancials.backend;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entity class representing a financial transaction in the application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {

    /**
     * Primary key for the transaction entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Many-to-one relationship with the Clothing entity.
     */
    @ManyToOne
    private Clothing clothing;

    /**
     * Name of the clothing associated with the transaction.
     */
    @NotBlank(message = "Clothing name cannot be blank")
    private String clothingName;

    /**
     * Profit generated from the transaction.
     */
    @Positive(message = "Profit must be a positive number")
    private Double profit;

    /**
     * Date of the transaction.
     */
    @NotNull(message = "Date cannot be empty")
    private LocalDate date;

    /**
     * Quantity of clothing items involved in the transaction.
     */
    @Positive(message = "Quantity must be a positive number")
    private Integer quantity;
}
