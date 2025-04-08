package online.devplanet.crud_application.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.devplanet.crud_application.CustomAnnotations.Annotations.ReservationDateConstraint;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    
    private int reservationId;

//    @NotNull(message = "User Id cannot be null")
//    @Min(value = 1, message = "User Id must be at least 1")
//    private int userId;

    @NotNull(message = "First Name cannot be null")
    @Size(min = 3, message = "First Name must have at least 3 characters")
    private String firstName;

    @NotNull(message = "Last Name cannot be null")
    @Size(min = 3, message = "Last Name must have at least 3 characters")
    private String lastName;

    @NotNull(message = "Mobile number cannot be null")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be exactly 10 digits and contain only numbers")
    private String mobileNo;

    @NotNull(message = "Reservation date cannot be empty")
    @FutureOrPresent(message = "Reservation date must be today or in the future")
    @ReservationDateConstraint
    private LocalDate reservationDate;

    @NotNull(message = "Reservation time cannot be empty")
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "Reservation time must be in the format HH:mm (24-hour format)")
    private String reservationTime;

    @NotNull(message = "Number of people cannot be empty")
    @Min(value = 1, message = "Number of people must be greater than 0")
    private int noOfPeople;

    private String reservationStatus = "booked";
//
//    @NotNull(message = "Restaurant Id cannot be null")
//    @Min(value = 1, message = "Restaurant Id must be at least 1")
    private int restaurantId;

}