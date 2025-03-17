package online.devplanet.crud_application.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import online.devplanet.crud_application.DTO.RestaurantDTO;
import online.devplanet.crud_application.Service.RestaurantService;
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

    // get by restaurant name
    @GetMapping("/restaurants/by-name")
    public ResponseEntity<?> getRestaurantByName(@RequestParam String restaurantName) {
        List<RestaurantDTO> restaurants = service.getRestaurantByName(restaurantName);
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    // get by restaurant email
    @GetMapping("/restaurants/by-email")
    public ResponseEntity<?> getRestaurantByEmail(@RequestParam String restaurantEmail) {
        RestaurantDTO restaurants = service.getRestaurantByEmail(restaurantEmail);
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    // get by restaurant phone
    @GetMapping("/restaurants/by-phone")
    public ResponseEntity<?> getRestaurantByPhone(@RequestParam String restaurantPhone) {
        RestaurantDTO restaurants = service.getRestaurantByPhone(restaurantPhone);
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }


    // search by any keyword
    @GetMapping("/restaurants/search")
    public ResponseEntity<?> searchRestaurant(@RequestParam String keyword) {
        List<RestaurantDTO> restaurants = service.searchRestaurant(keyword);
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

    // update restaurant
    @PutMapping("/restaurant/{id}")
    public ResponseEntity<?> updateRestaurant(@PathVariable int id, @Valid @RequestBody RestaurantDTO restaurantDTO) {
        service.updateRestaurant(id, restaurantDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Restaurant updated successfully");
    }

    // delete restaurant
    @DeleteMapping("/restaurant/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable int id) {
        service.deleteRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body("Restaurant deleted successfully");
    }
}
