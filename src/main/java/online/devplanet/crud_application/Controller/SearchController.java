package online.devplanet.crud_application.Controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import online.devplanet.crud_application.DTO.ReservationDTO;
import online.devplanet.crud_application.Service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;


    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam("mobileNo")
            @NotNull(message = "Mobile number cannot be empty")
            @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be exactly 10 digits and contain only numbers")
            String mobileNo) {

        List<ReservationDTO> reservations = searchService.search(mobileNo);

        return ResponseEntity.status(HttpStatus.OK).body(reservations);

    }

}
