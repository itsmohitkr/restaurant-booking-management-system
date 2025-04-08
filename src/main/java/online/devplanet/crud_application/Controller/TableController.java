package online.devplanet.crud_application.Controller;

import jakarta.validation.Valid;
import online.devplanet.crud_application.DTO.ReservationIdDTO;
import online.devplanet.crud_application.DTO.TableDTO;
import online.devplanet.crud_application.Service.ReservationService;
import online.devplanet.crud_application.Service.TableReservationService;
import online.devplanet.crud_application.Service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurant")
public class TableController {

    @Autowired
    private TableService tableService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TableReservationService tableReservationService;

    @GetMapping("/{restaurantId}/tables")
    public ResponseEntity<List<TableDTO>> getAllTables(@PathVariable int restaurantId) {
        List<TableDTO> tables = tableService.getAllTables(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(tables);
    }
    @PostMapping("/{restaurantId}/table")
    public ResponseEntity<Map<String, String>> addTable(@Valid @RequestBody TableDTO tableDTO, @PathVariable int restaurantId) {
        tableService.addTable(tableDTO, restaurantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Table added successfully"));
    }

    @PutMapping("/{restaurantId}/tables/{id}")
    public ResponseEntity<Map<String, String>> updateTable(@PathVariable int id, @Valid @RequestBody TableDTO tableDTO, @PathVariable int restaurantId) {
       tableService.updateTable(id, tableDTO, restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Table updated successfully"));
    }

    @PutMapping("/{restaurantId}/tables/{id}/seat-reservation")
    public ResponseEntity<?> seatReservation(@PathVariable int id, @Valid @RequestBody ReservationIdDTO reservationIdDTO, @PathVariable int restaurantId) {
        try {
            tableReservationService.seatReservation(id, reservationIdDTO.getReservationId(), restaurantId);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Reservation seated successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

}
