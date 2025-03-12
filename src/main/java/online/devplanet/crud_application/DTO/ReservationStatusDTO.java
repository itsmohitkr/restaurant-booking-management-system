package online.devplanet.crud_application.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationStatusDTO {

    @NotBlank(message = "Status cannot be empty")
    private String status;


}
