package online.devplanet.crud_application.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationIdDTO {

    @NotNull(message = "Reservation Id cannot be empty")
    @Min(value = 1, message = "Reservation Id must be greater than 0")
    private Integer reservationId;

}
