package online.devplanet.crud_application.Controller;

import jakarta.validation.Valid;
import online.devplanet.crud_application.DTO.RestaurantDTO;
import online.devplanet.crud_application.Service.RestaurantService;
import online.devplanet.crud_application.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    // get all restaurants
    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<RestaurantDTO>restaurants= service.getAllRestaurants();
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    // get restaurant by id
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable int id) {
        RestaurantDTO restaurantDTO = service.getRestaurantById(id);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
    }

    @PostMapping("/restaurant")
    public ResponseEntity<?> addRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        service.addRestaurant(restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant added successfully");
    }

    // get by restaurant name


}
