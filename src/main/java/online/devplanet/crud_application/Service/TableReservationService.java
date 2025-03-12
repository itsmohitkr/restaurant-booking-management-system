package online.devplanet.crud_application.Service;

import online.devplanet.crud_application.DTO.ReservationDTO;
import online.devplanet.crud_application.DTO.TableDTO;
import online.devplanet.crud_application.Mapper.ReservationMapper;
import online.devplanet.crud_application.Mapper.TableMapper;
import online.devplanet.crud_application.exception.CustomException;
import online.devplanet.crud_application.model.Reservations;
import online.devplanet.crud_application.model.Tables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableReservationService {

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private TableService tableService;

    @Autowired
    private ReservationService reservationService;

    @Transactional // Ensures rollback if any operation fails
    public void seatReservation(int tableId, int reservationId) {
        TableDTO tableDTO = tableService.getTableById(tableId);
        ReservationDTO reservationDTO = reservationService.getReservationById(reservationId);

        if (tableDTO.getReservationId() != 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Table is already reserved");
        }
        if (tableDTO.getCapacity() < reservationDTO.getNoOfPeople()) {
            throw new IllegalArgumentException("Table capacity is less than the number of people in the reservation");
        }

        // Update table and reservation
        tableDTO.setReservationId(reservationId);
        reservationDTO.setReservationStatus("SEATED");

        tableService.addTable(tableDTO);
        reservationService.updateReservationStatus(reservationDTO);
    }
}
