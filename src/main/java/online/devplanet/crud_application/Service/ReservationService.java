package online.devplanet.crud_application.Service;

import online.devplanet.crud_application.Config.UserAuthenticationToken;
import online.devplanet.crud_application.DTO.ReservationDTO;
import online.devplanet.crud_application.Mapper.ReservationMapper;
import online.devplanet.crud_application.Repository.ReservationRepository;
import online.devplanet.crud_application.Repository.RestaurantRepository;
import online.devplanet.crud_application.exception.CustomException;
import online.devplanet.crud_application.model.Reservations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    // get the owner id from the security context
    private int getOwnerIdFromSecurityContext() {
        UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return userAuthenticationToken.getOwnerId();
    }


    public List<ReservationDTO> getAllReservation(int restaurantId) {
        int ownerId = getOwnerIdFromSecurityContext();
        if (!restaurantRepository.existsByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }

        List<Reservations> reservations = reservationRepository.findAllByRestaurant_RestaurantIdAndRestaurant_RestaurantOwner_OwnerId(restaurantId, ownerId);
        if (reservations.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "there is no reservation present in this restaurant with ID: " + restaurantId);
        }

        return reservationMapper.toDTOList(reservations);
    }

    public void addReservation(ReservationDTO reservationDTO, int restaurantId) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check if the restaurant belongs to the owner
        if (!restaurantRepository.existsByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }
        reservationDTO.setRestaurantId(restaurantId);
        Reservations reservation = reservationMapper.toEntity(reservationDTO);
        reservationRepository.save(reservation);
    }

    public ReservationDTO getReservationById(int restaurantId, int reservationId) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check if the restaurant belongs to the owner
        if (!restaurantRepository.existsByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }

        Reservations reservation = reservationRepository.findByReservationIdAndRestaurant_RestaurantIdAndRestaurant_RestaurantOwner_OwnerId(reservationId, restaurantId, ownerId);
        if (reservation == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Reservation not found with id: " + reservationId);
        }
        return reservationMapper.toDTO(reservation);
    }

    public void updateReservationStatus(ReservationDTO reservationDTO, int restaurantId) {
        addReservation(reservationDTO, restaurantId);
    }

    public void updateReservation(ReservationDTO reservationDTO, int restaurantId) {
        addReservation(reservationDTO, restaurantId);
    }

    public List<ReservationDTO> getReservationByMobileNo(int restaurantId, String mobileNo) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check if the restaurant belongs to the owner
        if (!restaurantRepository.existsByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }
        List<Reservations> reservation = reservationRepository.findByMobileNoAndRestaurant_RestaurantIdAndRestaurant_RestaurantOwner_OwnerId(mobileNo, restaurantId, ownerId);
        if (reservation.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Reservation not found with mobile number: " + mobileNo);
        }

        return reservationMapper.toDTOList(reservation);
    }

    public List<ReservationDTO> getReservationByReservationDate(int restaurantId, String date) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check if the restaurant belongs to the owner
        if (!restaurantRepository.existsByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }

        List<Reservations> reservation = reservationRepository.findByReservationDateAndRestaurant_RestaurantIdAndRestaurant_RestaurantOwner_OwnerId(date, restaurantId, ownerId);
        if (reservation.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Reservation not found with reservation date: " + date);
        }

        return reservationMapper.toDTOList(reservation);
    }

    public List<ReservationDTO> getReservationByFirstNameAndLastName(int restaurantId, String firstName, String lastName) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check if the restaurant belongs to the owner
        if (!restaurantRepository.existsByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }

        List<Reservations> reservation = reservationRepository.findByFirstNameAndLastNameAndRestaurant_RestaurantIdAndRestaurant_RestaurantOwner_OwnerId(firstName, lastName, restaurantId, ownerId);
        if (reservation.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Reservation not found with first name: " + firstName + " and last name: " + lastName);
        }

        return reservationMapper.toDTOList(reservation);
    }
}
