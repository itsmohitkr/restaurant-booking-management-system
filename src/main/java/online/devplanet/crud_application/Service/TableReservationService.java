package online.devplanet.crud_application.Service;

import online.devplanet.crud_application.Config.UserAuthenticationToken;
import online.devplanet.crud_application.DTO.ReservationDTO;
import online.devplanet.crud_application.DTO.TableDTO;
import online.devplanet.crud_application.Mapper.ReservationMapper;
import online.devplanet.crud_application.Mapper.TableMapper;
import online.devplanet.crud_application.Repository.ReservationRepository;
import online.devplanet.crud_application.Repository.RestaurantRepository;
import online.devplanet.crud_application.Repository.TableRepository;
import online.devplanet.crud_application.exception.CustomException;
import online.devplanet.crud_application.model.Reservations;
import online.devplanet.crud_application.model.Tables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableReservationService {

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private TableService tableService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private int getOwnerIdFromSecurityContext() {
        UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return userAuthenticationToken.getOwnerId();
    }

    @Transactional // Ensures rollback if any operation fails
    public void seatReservation(int tableId, int reservationId, int restaurantId) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check restaurant id belongs to the owner
        if (!restaurantRepository.existsByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }
        // check if reservation id belongs to the restaurant
        if (!reservationRepository.existsByReservationIdAndRestaurant_RestaurantId(reservationId, restaurantId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Reservation not found with id: " + reservationId);
        }

        // check if table id belongs to the restaurant
        if (!tableRepository.existsByTableIdAndRestaurant_RestaurantId(tableId, restaurantId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Table not found with id: " + tableId);
        }


        TableDTO tableDTO = tableService.getTableById(tableId);
        ReservationDTO reservationDTO = reservationService.getReservationById(restaurantId, reservationId);

        if (tableDTO.getReservationId() != 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Table is already reserved");
        }
        if (tableDTO.getCapacity() < reservationDTO.getNoOfPeople()) {
            throw new IllegalArgumentException("Table capacity is less than the number of people in the reservation");
        }

        if (reservationDTO.getReservationStatus().equals("cancelled")) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Reservation has already cancelled");
        }
        else if (reservationDTO.getReservationStatus().equals("booked")) {
            reservationDTO.setReservationStatus("seated");
        }
        tableDTO.setReservationId(reservationId);
        tableService.addTable(tableDTO,restaurantId);
        reservationService.updateReservationStatus(reservationDTO, restaurantId);
    }
}
