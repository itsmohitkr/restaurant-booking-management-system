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
}
