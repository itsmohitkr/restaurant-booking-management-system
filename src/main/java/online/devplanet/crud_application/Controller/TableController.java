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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class TableController {

    @Autowired
    private TableService tableService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TableReservationService tableReservationService;

    @GetMapping("tables")
    public ResponseEntity<List<TableDTO>> getAllTables() {
        List<TableDTO> tables = tableService.getAllTables();
        return ResponseEntity.status(HttpStatus.OK).body(tables);
    }
    @PostMapping("tables")
    public ResponseEntity<Map<String, String>> addTable(@Valid @RequestBody TableDTO tableDTO) {
        tableService.addTable(tableDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Table added successfully"));
    }

    @PutMapping("/table/{id}")
    public ResponseEntity<Map<String, String>> updateTable(@PathVariable int id, @Valid @RequestBody TableDTO tableDTO) {
       tableService.updateTable(id, tableDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Table updated successfully"));
    }

    @PutMapping("table/{id}/seat")
    public ResponseEntity<?> seat(@PathVariable int id, @Valid @RequestBody ReservationIdDTO reservationIdDTO) {
        try {
            tableReservationService.seatReservation(id, reservationIdDTO.getReservationId());
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Reservation seated successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

}
