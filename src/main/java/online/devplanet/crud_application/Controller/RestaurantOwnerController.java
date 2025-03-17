package online.devplanet.crud_application.Controller;

import jakarta.validation.Valid;
import online.devplanet.crud_application.DTO.RestaurantOwnerDTO;
import online.devplanet.crud_application.Service.RestaurantOwnerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestaurantOwnerController {

    @Autowired
    private RestaurantOwnerService service;

    // get all restaurant-owners
    @GetMapping("/restaurant-owners")
    public ResponseEntity<List<RestaurantOwnerDTO>> getAllRestaurantOwners() {

        List<RestaurantOwnerDTO> restaurantOwners = service.getAllRestaurantOwners();
        return ResponseEntity.status(HttpStatus.OK).body(restaurantOwners);
    }

    // get restaurant-owner by email
    @GetMapping("/restaurant-owner/by-email")
    public ResponseEntity<?> getRestaurantOwnerByEmail(@RequestParam String ownerEmail) {
        List<RestaurantOwnerDTO> restaurantOwnerDTO = service.getRestaurantOwnerByEmail(ownerEmail);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantOwnerDTO);
    }

    // get restaurant-owner by name
    @GetMapping("/restaurant-owner/by-name")
    public ResponseEntity<?> getRestaurantOwnerByName(@RequestParam String ownerName) {
        List<RestaurantOwnerDTO> restaurantOwnerDTO = service.getRestaurantOwnerByName(ownerName);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantOwnerDTO);
    }

    // get restaurant-owner by id
    @GetMapping("/restaurant-owner/{id}")
    public ResponseEntity<?> getRestaurantOwnerById(@PathVariable int id) {
        RestaurantOwnerDTO restaurantOwnerDTO = service.getRestaurantOwnerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantOwnerDTO);
    }

    // create a new restaurant-owner
    @PostMapping("/restaurant-owner")
    public ResponseEntity<?> addRestaurantOwner(@Valid @RequestBody RestaurantOwnerDTO restaurantOwnerDTO) {
        service.addRestaurantOwner(restaurantOwnerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant Owner added successfully");
    }

    // update restaurant-owner
    @PutMapping("/restaurant-owner/{id}")
    public ResponseEntity<?> updateRestaurantOwner(@PathVariable int id, @Valid @RequestBody RestaurantOwnerDTO restaurantOwnerDTO) {
        service.updateRestaurantOwner(id, restaurantOwnerDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Restaurant Owner updated successfully");
    }

}
