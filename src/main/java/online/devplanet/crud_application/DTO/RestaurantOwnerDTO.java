package online.devplanet.crud_application.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.devplanet.crud_application.CustomAnnotations.Annotations.Trimmed;
import online.devplanet.crud_application.model.RestaurantOwner;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantOwnerDTO {

    private int ownerId; // This will be auto-generated if not provided

    @NotNull(message = "Owner name cannot be empty")
    @Size(min = 3, message = "Owner name must be at least 3 characters long")
    @Trimmed
    private String ownerName;

    @NotNull(message = "Owner contact cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Owner contact must be exactly 10 digits and contain only numbers")
    @Trimmed
    private String ownerContact;

    @NotNull(message = "Owner email cannot be empty")
    @Email(message = "Invalid email format")
    @Trimmed
    private String ownerEmail;

    private List<RestaurantDTO> restaurants;
}