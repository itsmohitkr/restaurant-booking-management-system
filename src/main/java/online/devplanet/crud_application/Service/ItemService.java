package online.devplanet.crud_application.Service;

import jakarta.validation.Valid;
import online.devplanet.crud_application.DTO.ItemDTO;
import online.devplanet.crud_application.Mapper.ItemMapper;
import online.devplanet.crud_application.Repository.ItemRepository;
import online.devplanet.crud_application.Repository.MenuRepository;
import online.devplanet.crud_application.exception.CustomException;
import online.devplanet.crud_application.model.Items;
import online.devplanet.crud_application.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    // add the implementation here
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ItemMapper itemMapper;

    public List<ItemDTO> getAllItems(int menuId) {
        // check if menu exists
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Menu not found with id: " + menuId));
        return itemMapper.toDTOList(itemRepository.findByMenu_MenuId(menuId));


    }

    public void addItem(int menuId, ItemDTO itemDTO) {
        // check if menu_id is equal to the menu_id present in the item DQ
        if (menuId != itemDTO.getMenuId()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Menu id in path variable and item do not match");
        }
        // check if menu exists
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Menu not found with id: " + menuId));

        itemRepository.save(itemMapper.toEntity(itemDTO));

    }

    public void updateItem(int menuId, int itemId, @Valid ItemDTO itemDTO) {

        if (menuId != itemDTO.getMenuId()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Menu id in path variable and item do not match");
        }
        // check if menu exists
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Menu not found with id: " + menuId));
        // check if item exists in the menu

        Items existingItem = itemRepository.findById(itemId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Item not found with ID: " + itemId));

        Items updatedItem = itemMapper.toEntity(itemDTO);
        updatedItem.setItemId(existingItem.getItemId());
        updatedItem.setMenu(menu);
        itemRepository.save(updatedItem);
    }

    public ItemDTO getItemById(int menuId, int itemId) {
        // check if menu exists
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Menu not found with id: " + menuId));
        // check if item exists in the menu
        Items item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Item not found with ID: " + itemId));

        return itemMapper.toDTO(item);
    }

    public List<ItemDTO> searchItem(int menuId, String keyword) {
        // check if menu exists
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Menu not found with id: " + menuId));
        // search for items with name, description
        return itemMapper.toDTOList(itemRepository.findByMenu_MenuIdAndItemNameContainingOrItemDescriptionContaining(menuId, keyword, keyword));
    }

    public void deleteItem(int menuId, int itemId) {
        // check if menu exists
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Menu not found with id: " + menuId));
        // check if item exists in the menu
        Items item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Item not found with ID: " + itemId));
        if(item.getMenu().getMenuId() != menuId) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Item does not belong to the menu");
        }
        itemRepository.delete(item);

    }
}
