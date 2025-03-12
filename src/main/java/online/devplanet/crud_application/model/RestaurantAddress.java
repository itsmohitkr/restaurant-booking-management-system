package online.devplanet.crud_application.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantAddress {


    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
