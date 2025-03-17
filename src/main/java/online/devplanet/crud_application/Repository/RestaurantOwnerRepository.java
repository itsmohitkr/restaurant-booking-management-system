package online.devplanet.crud_application.Repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import online.devplanet.crud_application.model.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Integer> {

    boolean existsByOwnerEmail(@NotNull(message = "Owner email cannot be empty") @Email(message = "Invalid email format") String ownerEmail);

    List<RestaurantOwner> findByOwnerNameAndOwnerEmail(String ownerName, String ownerEmail);

    List<RestaurantOwner> findByOwnerName(String ownerName);

    List<RestaurantOwner> findByOwnerEmail(String ownerEmail);
}
