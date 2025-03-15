package online.devplanet.crud_application.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private int itemId; // This will be auto-generated if not provided

    @NotNull(message = "Item name cannot be null")
    @Size(min = 2, max = 50, message = "Item name must be between 2 and 50 characters")
    private String itemName;

    @NotNull(message = "Item description cannot be null")
    @Size(min = 5, max = 200, message = "Item description must be between 5 and 200 characters")
    private String itemDescription;

    @NotNull(message = "Item price cannot be null")
    @Positive(message = "Item price must be greater than zero")
    private Double itemPrice;

    @NotNull(message = "Menu ID cannot be null")
    @Min(value = 1, message = "Menu ID must be a positive integer")
    private Integer menuId;
}