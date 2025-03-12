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
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService service;


    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations(@RequestParam(required = false) String date) {
        List<ReservationDTO> reservations;
        if (date != null && !date.isBlank()) {
            reservations = service.getReservationByReservationDate(date);
        }
        else {
            reservations = service.getAllReservation();
        }
        return ResponseEntity.status(HttpStatus.OK).body(reservations);
    }


    @PostMapping
    public ResponseEntity<Map<String, String>> addReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        service.addReservation(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Reservation added successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable int id) {
        ReservationDTO reservationDTO = service.getReservationById(id);
        return ResponseEntity.status(HttpStatus.OK).body(reservationDTO);
    }

    // get reservation by mobile number
    @GetMapping("/by-mobile")
    public ResponseEntity<?> getReservationByMobileNo(@RequestParam String mobileNo) {
        List<ReservationDTO> reservationDTO = service.getReservationByMobileNo(mobileNo);
        return ResponseEntity.status(HttpStatus.OK).body(reservationDTO);
    }

    @GetMapping("/by-date")
    public ResponseEntity<?> getReservationByReservationDate(@RequestParam String date) {
        List<ReservationDTO> reservationDTO = service.getReservationByReservationDate(date);
        return ResponseEntity.status(HttpStatus.OK).body(reservationDTO);
    }

    // get reservation by reservation firstname and lastname
    @GetMapping("/by-name")
    public ResponseEntity<?> getReservationByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        List<ReservationDTO> reservationDTO = service.getReservationByFirstNameAndLastName(firstName, lastName);
        return ResponseEntity.status(HttpStatus.OK).body(reservationDTO);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<Map<String, String>> updateReservation(@PathVariable int id, @Valid @RequestBody ReservationDTO  reservationDTO) {
        ReservationDTO reservation = service.getReservationById(id);
        reservationDTO.setReservationId(id);
        service.updateReservation(reservationDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Reservation updated successfully"));
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateReservationStatus(@PathVariable int id, @Valid @RequestBody ReservationStatusDTO statusDTO) {
        ReservationDTO reservation = service.getReservationById(id);
        reservation.setReservationStatus(statusDTO.getStatus());
        service.updateReservationStatus(reservation);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Reservation status updated successfully"));
    }

}

