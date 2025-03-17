package online.devplanet.crud_application.Repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import online.devplanet.crud_application.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    List<Restaurant> findByRestaurantOwner_OwnerId(Integer ownerId);

    boolean existsByRestaurantEmail(@NotNull(message = "RestaurantEmail cannot be empty") @Email(message = "Email should be valid") String restaurantEmail);

    List<Restaurant> findByRestaurantNameContainingOrRestaurantContactContainingOrRestaurantEmailContainingOrRestaurantLocationContaining(String keyword, String keyword1, String keyword2, String keyword3);

    List<Restaurant> findByRestaurantName(String restaurantName);

    Restaurant findByRestaurantEmail(String restaurantEmail);

    Restaurant findByRestaurantContact(String restaurantPhone);

    boolean existsByRestaurantContact(@NotNull(message = "RestaurantContact cannot be empty") @Pattern(regexp = "^[0-9]{10}$", message = "restaurantContact must be exactly 10 digits and contain only numbers") String restaurantContact);
}
