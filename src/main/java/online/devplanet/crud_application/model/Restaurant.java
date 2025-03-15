package online.devplanet.crud_application.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurants") // This annotation is used to specify the table name in the database
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "restaurantId"
)
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restaurantId;

    private String restaurantName;
    private String restaurantContact;
    private String restaurantEmail;
    private String restaurantLocation;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "ownerId")
    private RestaurantOwner restaurantOwner;

    @Embedded
    private RestaurantAddress restaurantAddress;
}
