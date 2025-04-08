package online.devplanet.crud_application.Controller;

import jakarta.validation.Valid;
import online.devplanet.crud_application.DTO.MenuDTO;
import online.devplanet.crud_application.Service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurant")
public class MenuController {


    @Autowired
    private MenuService service;

    // create new menu
    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<?> getMenu(@PathVariable int restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getMenu(restaurantId));
    }

    @PostMapping("/{restaurantId}/menu")
    public ResponseEntity<?> addMenu(@PathVariable int restaurantId) {
        service.addMenu(restaurantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Menu added successfully"));
    }

}
