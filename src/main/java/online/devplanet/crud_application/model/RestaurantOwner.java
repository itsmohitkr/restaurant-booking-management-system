package online.devplanet.crud_application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant_owners")
public class RestaurantOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ownerId;

    private String ownerName;
    private String ownerContact;
    private String ownerEmail;

    @ElementCollection // This annotation allows storing a collection of basic types
    @CollectionTable(name = "owner_restaurants", joinColumns = @JoinColumn(name = "owner_id"))
    @Column(name = "restaurant_id")
    private List<Integer> restaurantIds;
}
