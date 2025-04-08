package online.devplanet.crud_application.Controller;

import jakarta.validation.Valid;
import online.devplanet.crud_application.DTO.ReservationDTO;
import online.devplanet.crud_application.DTO.ReservationStatusDTO;
import online.devplanet.crud_application.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurant")
public class ReservationController {

    @Autowired
    private ReservationService service;

    @GetMapping("/{restaurantId}/reservations")
    public ResponseEntity<List<ReservationDTO>> getAllReservations(@RequestParam(required = false) String date, @PathVariable int restaurantId) {
        List<ReservationDTO> reservations;
        if (date != null && !date.isBlank()) {
            reservations = service.getReservationByReservationDate(restaurantId,date);
        }
        else {
            reservations = service.getAllReservation(restaurantId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(reservations);
    }

    @PostMapping("/{restaurantId}/reservation")
    public ResponseEntity<Map<String, String>> addReservation(@Valid @RequestBody ReservationDTO reservationDTO, @PathVariable int restaurantId) {
        service.addReservation(reservationDTO, restaurantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Reservation added successfully"));
    }

    @GetMapping("/{restaurantId}/reservation/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable int restaurantId, @PathVariable int id) {
        ReservationDTO reservationDTO = service.getReservationById(restaurantId, id);
        return ResponseEntity.status(HttpStatus.OK).body(reservationDTO);
    }

    // get reservation by mobile number
    @GetMapping("/{restaurantId}/reservation/by-mobile")
    public ResponseEntity<?> getReservationByMobileNo(@PathVariable int restaurantId, @RequestParam String mobileNo) {
        List<ReservationDTO> reservationDTO = service.getReservationByMobileNo(restaurantId, mobileNo);
        return ResponseEntity.status(HttpStatus.OK).body(reservationDTO);
    }

    @GetMapping("/{restaurantId}/reservation/by-date")
    public ResponseEntity<?> getReservationByReservationDate(@PathVariable int restaurantId, @RequestParam String date) {
        List<ReservationDTO> reservationDTO = service.getReservationByReservationDate(restaurantId, date);
        return ResponseEntity.status(HttpStatus.OK).body(reservationDTO);
    }

    // get reservation by reservation firstname and lastname
    @GetMapping("/{restaurantId}/reservation/by-name")
    public ResponseEntity<?> getReservationByFirstNameAndLastName(@PathVariable int restaurantId, @RequestParam String firstName, @RequestParam String lastName) {
        List<ReservationDTO> reservationDTO = service.getReservationByFirstNameAndLastName(restaurantId, firstName, lastName);
        return ResponseEntity.status(HttpStatus.OK).body(reservationDTO);
    }

    @PutMapping("/{restaurantId}/reservation/{id}/edit")
    public ResponseEntity<Map<String, String>> updateReservation(@PathVariable int restaurantId,@PathVariable int id, @Valid @RequestBody ReservationDTO  reservationDTO) {
        ReservationDTO reservation = service.getReservationById(restaurantId, id);
        reservationDTO.setReservationId(id);
        service.updateReservation(reservationDTO, restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Reservation updated successfully"));
    }

    // will check later
    @PutMapping("/{restaurantId}/reservation/{id}/status")
    public ResponseEntity<?> updateReservationStatus(@PathVariable int restaurantId, @PathVariable int id, @Valid @RequestBody ReservationStatusDTO statusDTO) {
        ReservationDTO reservation = service.getReservationById(restaurantId, id);
        reservation.setReservationStatus(statusDTO.getStatus());
        service.updateReservationStatus(reservation, restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Reservation status updated successfully"));
    }
}

