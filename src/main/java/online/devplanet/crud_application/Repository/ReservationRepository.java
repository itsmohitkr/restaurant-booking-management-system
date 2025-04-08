package online.devplanet.crud_application.Repository;

import online.devplanet.crud_application.model.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Integer> {

    List<Reservations> findByMobileNo(String mobileNo);

    List<Reservations> findByReservationDate(String reservationDate);

    List<Reservations> findByFirstNameAndLastName(String firstName, String lastName);

    List<Reservations> findAllByRestaurant_RestaurantId(int restaurantId);

    List<Reservations> findAllByRestaurant_RestaurantIdAndRestaurant_RestaurantOwner_OwnerId(int restaurantId, int ownerId);

    Reservations findByReservationIdAndRestaurant_RestaurantIdAndRestaurant_RestaurantOwner_OwnerId(int reservationId, int restaurantId, int ownerId);

    List<Reservations> findByMobileNoAndRestaurant_RestaurantIdAndRestaurant_RestaurantOwner_OwnerId(String mobileNo, int restaurantId, int ownerId);

    List<Reservations> findByReservationDateAndRestaurant_RestaurantIdAndRestaurant_RestaurantOwner_OwnerId(String date, int restaurantId, int ownerId);

    List<Reservations> findByFirstNameAndLastNameAndRestaurant_RestaurantIdAndRestaurant_RestaurantOwner_OwnerId(String firstName, String lastName, int restaurantId, int ownerId);

    boolean existsByReservationIdAndRestaurant_RestaurantIdAndRestaurant_RestaurantOwner_OwnerId(int reservationId, int restaurantId, int ownerId);

    boolean existsByReservationIdAndRestaurant_RestaurantId(int reservationId, int restaurantId);

    Reservations findByReservationIdAndRestaurant_RestaurantId(int restaurantId, int reservationId);
}
