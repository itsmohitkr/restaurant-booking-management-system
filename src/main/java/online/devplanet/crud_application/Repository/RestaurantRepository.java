package online.devplanet.crud_application.Repository;

import jakarta.validation.constraints.NotNull;
import online.devplanet.crud_application.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    List<Restaurant> findByRestaurantOwner_OwnerId(Integer ownerId);
}
