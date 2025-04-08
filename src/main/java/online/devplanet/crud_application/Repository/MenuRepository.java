package online.devplanet.crud_application.Repository;

import online.devplanet.crud_application.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    Menu findByRestaurant_RestaurantId(int restaurantId);

    Menu findByMenuIdAndRestaurant_RestaurantId(int menuId, int restaurantId);
}
