package online.devplanet.crud_application.Controller;

import jakarta.validation.Valid;
import online.devplanet.crud_application.DTO.ItemDTO;
import online.devplanet.crud_application.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // get all items
    @GetMapping("/{restaurantId}/menu/{menuId}/items")
    public ResponseEntity<List<ItemDTO>> getAllItems(@PathVariable int restaurantId, @PathVariable int menuId) {
        List<ItemDTO> items = itemService.getAllItems(menuId, restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    // create item
    @PostMapping("/{restaurantId}/menu/{menuId}/item")
    public ResponseEntity<?> addItem(@PathVariable int restaurantId, @PathVariable int menuId, @Valid @RequestBody ItemDTO itemDTO) {
        itemService.addItem(restaurantId, menuId, itemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added successfully");
    }

    @PutMapping("/{restaurantId}/menu/{menuId}/item/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable int restaurantId, @PathVariable int menuId, @PathVariable int itemId, @Valid @RequestBody ItemDTO itemDTO) {
        itemService.updateItem(restaurantId, menuId, itemId, itemDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Item updated successfully");
    }

    @GetMapping("/{restaurantId}/menu/{menuId}/item/{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable int restaurantId, @PathVariable int menuId, @PathVariable int itemId) {
        ItemDTO itemDTO = itemService.getItemById(restaurantId, menuId, itemId);
        return ResponseEntity.status(HttpStatus.OK).body(itemDTO);
    }

    // search by any keyword
    @GetMapping("/{restaurantId}/menu/{menuId}/item/search")
    public ResponseEntity<?> searchItem(@PathVariable int restaurantId, @PathVariable int menuId, @RequestParam String keyword) {
        List<ItemDTO> items = itemService.searchItem(restaurantId, menuId, keyword);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @DeleteMapping("/{restaurantId}/menu/{menuId}/item/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable int menuId, @PathVariable int itemId, @PathVariable int restaurantId) {
        itemService.deleteItem(menuId, itemId, restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body("Item deleted successfully");
    }

}
