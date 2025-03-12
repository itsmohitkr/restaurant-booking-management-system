package online.devplanet.crud_application.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantAddressDTO {

    @NotNull(message = "Street cannot be empty")
    private String street;

    @NotNull(message = "City cannot be empty")
    private String city;

    @NotNull(message = "State cannot be empty")
    private String state;

    @NotNull(message = "Country cannot be empty")
    private String country;

    @NotNull(message = "Zip code cannot be empty")
    @Pattern(regexp = "^[0-9]{6}$", message = "Zip code must be 6 digits")
    private String zipCode;
}