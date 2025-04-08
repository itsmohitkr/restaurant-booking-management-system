package online.devplanet.crud_application.Repository;


import online.devplanet.crud_application.model.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<Tables, Integer> {
    boolean existsByRestaurant_RestaurantIdAndRestaurant_RestaurantOwner_OwnerId(int restaurantId, int ownerId);

    List<Tables> findAllByRestaurant_RestaurantIdAndRestaurant_RestaurantOwner_OwnerId(int restaurantId, int ownerId);

    boolean existsByTableIdAndRestaurant_RestaurantId(int id, int restaurantId);

    Tables findByTableId(int id);
}
