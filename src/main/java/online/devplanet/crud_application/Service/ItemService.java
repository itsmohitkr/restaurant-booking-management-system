package online.devplanet.crud_application.Service;

import jakarta.validation.Valid;
import online.devplanet.crud_application.Config.UserAuthenticationToken;
import online.devplanet.crud_application.DTO.ItemDTO;
import online.devplanet.crud_application.Mapper.ItemMapper;
import online.devplanet.crud_application.Repository.ItemRepository;
import online.devplanet.crud_application.Repository.MenuRepository;
import online.devplanet.crud_application.Repository.RestaurantRepository;
import online.devplanet.crud_application.exception.CustomException;
import online.devplanet.crud_application.model.Items;
import online.devplanet.crud_application.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ItemMapper itemMapper;


    // get the owner id from the security context
    private int getOwnerIdFromSecurityContext() {
        UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return userAuthenticationToken.getOwnerId();
    }

    public List<ItemDTO> getAllItems(int menuId, int restaurantId) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check if the restaurant belongs to the owner
        if (restaurantRepository.findByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId) == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }
        Menu menu = menuRepository.findByMenuIdAndRestaurant_RestaurantId(menuId,restaurantId);
        if (menu == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Menu not found with id: " + menuId);
        }
        return itemMapper.toDTOList(itemRepository.findAllByMenu_MenuId(menuId));

    }

    public void addItem(int restaurantId, int menuId, @Valid ItemDTO itemDTO) {
        int ownerId = getOwnerIdFromSecurityContext();

        if (restaurantRepository.findByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId) == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Menu not found with id: " + menuId));


        Items item = itemMapper.toEntity(itemDTO);
        item.setMenu(menu);
        itemRepository.save(item);
    }

    public void updateItem( int restaurantId, int menuId, int itemId, @Valid ItemDTO itemDTO) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check if the restaurant belongs to the owner
        if (restaurantRepository.findByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId) == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }
        Menu menu = menuRepository.findByMenuIdAndRestaurant_RestaurantId(menuId, restaurantId);
        if (menu == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Menu not found with id: " + menuId);
        }

        Items existingItem = itemRepository.findById(itemId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Item not found with ID: " + itemId));

        Items updatedItem = itemMapper.toEntity(itemDTO);
        updatedItem.setItemId(existingItem.getItemId());
        updatedItem.setMenu(menu);
        itemRepository.save(updatedItem);
    }

    public ItemDTO getItemById( int restaurantId, int menuId, int itemId) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check if the restaurant belongs to the owner
        if (restaurantRepository.findByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId) == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }
        Menu menu = menuRepository.findByMenuIdAndRestaurant_RestaurantId(menuId, restaurantId);
        if (menu == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Menu not found with id: " + menuId);
        }

        Items item = itemRepository.findById(itemId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Item not found with ID: " + itemId));


        return itemMapper.toDTO(item);
    }

    public List<ItemDTO> searchItem( int restaurantId, int menuId, String keyword) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check if the restaurant belongs to the owner
        if (restaurantRepository.findByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId) == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }
        Menu menu = menuRepository.findByMenuIdAndRestaurant_RestaurantId(menuId, restaurantId);
        if (menu == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Menu not found with id: " + menuId);
        }

        return itemMapper.toDTOList(itemRepository.findAllByMenu_MenuIdAndItemNameContainingOrItemDescriptionContaining(menuId, keyword, keyword));
    }

    public void deleteItem(int menuId, int itemId, int restaurantId) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check if the restaurant belongs to the owner
        if (restaurantRepository.findByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId) == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }
        // check if the menu belongs to the restaurant
        if (menuRepository.findByMenuIdAndRestaurant_RestaurantId(menuId, restaurantId) == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Menu not found with id: " + menuId);
        }

        Items item = itemRepository.findByItemIdAndMenu_MenuId(itemId, menuId);
        if (item == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Item not found with id: " + itemId);
        }
        itemRepository.delete(item);

    }
}
