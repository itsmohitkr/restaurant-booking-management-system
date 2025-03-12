package online.devplanet.crud_application.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDTO {
    private Integer tableId;

    @NotNull(message = "User Id cannot be empty")
    @Min(value = 1, message = "User Id must be at least 1")
    private Integer userId;

    @NotNull(message = "Table Name cannot be empty")
    private String tableName;

    @NotNull(message = "Capacity cannot be empty")
    @Min(value = 1, message = "Capacity must be greater than 0")
    private Integer capacity;

    @Min(value = 0, message = "Reservation Id must be greater than or equal to 0")
    private Integer reservationId;

}
