package online.devplanet.crud_application.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {

    private Integer menuId; // No validation needed since this is usually auto-generated

    @NotNull(message = "Restaurant ID cannot be null")
    @Min(value = 1, message = "Restaurant ID must be a positive integer")
    private Integer restaurantId;
}