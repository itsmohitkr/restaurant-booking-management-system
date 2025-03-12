package online.devplanet.crud_application.DTO;


import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import online.devplanet.crud_application.model.RestaurantAddress;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {

    private int restaurantId;

    @NotNull(message = "RestaurantName cannot be empty")
    @Min(value = 3, message = "RestaurantName must be at least 1")
    private String restaurantName;

    @NotNull(message = "RestaurantContact cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "restaurantContact must be exactly 10 digits and contain only numbers")
    private String restaurantContact;

    @NotNull(message = "RestaurantEmail cannot be empty")
    @Email(message = "Email should be valid")
    private String restaurantEmail;

    @NotNull(message = "RestaurantLocation cannot be empty")
    @Min(value = 3, message = "RestaurantLocation must be at least 3")
    @Max(value = 50, message = "RestaurantLocation must be at most 30 characters")
    private String restaurantLocation;

    @NotNull(message = "OwnerId cannot be empty")
    private Integer ownerId;

    @NotNull(message = "RestaurantAddress cannot be empty")
    @Valid
    private RestaurantAddress restaurantAddress;


}
