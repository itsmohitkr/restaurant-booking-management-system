package online.devplanet.crud_application.Controller;

import online.devplanet.crud_application.DTO.RestaurantOwnerDTO;
import online.devplanet.crud_application.Service.RestaurantOwnerService;
import online.devplanet.crud_application.model.RestaurantOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant-owner")
public class RestaurantOwnerController {

    @Autowired
    private RestaurantOwnerService service;

    // get all restaurant-owners
    @GetMapping
    public ResponseEntity<List<RestaurantOwnerDTO>> getAllRestaurantOwners() {
        List<RestaurantOwnerDTO>restaurantOwners = service.getAllRestaurantOwners();
        return ResponseEntity.status(HttpStatus.OK).body(restaurantOwners);

    }

    // get restaurant-owner by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurantOwnerById(@PathVariable int id) {
        RestaurantOwnerDTO restaurantOwnerDTO = service.getRestaurantOwnerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantOwnerDTO);
    }

    // create a new restaurant-owner
    @PostMapping
    public ResponseEntity<?> addRestaurantOwner(@RequestBody RestaurantOwnerDTO restaurantOwnerDTO) {
        service.addRestaurantOwner(restaurantOwnerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant Owner added successfully");
    }

}
