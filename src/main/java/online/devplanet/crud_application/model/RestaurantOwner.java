package online.devplanet.crud_application.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant_owners")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "ownerId"
)
public class RestaurantOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ownerId;

    private String ownerName;
    private String ownerContact;
    private String ownerEmail;
    private String ownerPassword;

    private String ownerRole = "ADMIN";

    @OneToMany(mappedBy = "restaurantOwner", cascade = CascadeType.ALL)
    private List<Restaurant> restaurants=new ArrayList<>();

}
