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
@RequestMapping("/api/menu/{menuId}")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // get all items
    @GetMapping("/items")
    public ResponseEntity<List<ItemDTO>> getAllItems(@PathVariable int menuId) {
        List<ItemDTO> items = itemService.getAllItems(menuId);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    // create item
    @PostMapping("/item")
    public ResponseEntity<?> addItem(@PathVariable int menuId, @Valid @RequestBody ItemDTO itemDTO) {
        itemService.addItem(menuId, itemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added successfully");
    }

    @PutMapping("/item/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable int menuId, @PathVariable int itemId, @Valid @RequestBody ItemDTO itemDTO) {
        itemService.updateItem(menuId, itemId, itemDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Item updated successfully");
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable int menuId, @PathVariable int itemId) {
        ItemDTO itemDTO = itemService.getItemById(menuId, itemId);
        return ResponseEntity.status(HttpStatus.OK).body(itemDTO);
    }

    // search by any keyword
    @GetMapping("/items/search")
    public ResponseEntity<?> searchItem(@PathVariable int menuId, @RequestParam String keyword) {
        List<ItemDTO> items = itemService.searchItem(menuId, keyword);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable int menuId, @PathVariable int itemId) {
        itemService.deleteItem(menuId, itemId);
        return ResponseEntity.status(HttpStatus.OK).body("Item deleted successfully");
    }

}
