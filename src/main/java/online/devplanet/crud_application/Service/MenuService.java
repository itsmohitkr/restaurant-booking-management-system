package online.devplanet.crud_application.Service;

import jakarta.validation.Valid;
import online.devplanet.crud_application.Config.UserAuthenticationToken;
import online.devplanet.crud_application.DTO.MenuDTO;
import online.devplanet.crud_application.Mapper.MenuMapper;
import online.devplanet.crud_application.Repository.MenuRepository;
import online.devplanet.crud_application.Repository.RestaurantRepository;
import online.devplanet.crud_application.exception.CustomException;
import online.devplanet.crud_application.model.Menu;
import online.devplanet.crud_application.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuRepository repository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuMapper mapper;


    // get the owner id from the security context
    private int getOwnerIdFromSecurityContext() {
        UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return userAuthenticationToken.getOwnerId();
    }

    public void addMenu(int restaurantId) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check id restaurant exists
        Restaurant restaurant = restaurantRepository.findByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId,ownerId);
        if (restaurant == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }
        Menu menu = new Menu();
        menu.setRestaurant(restaurant);
        repository.save(menu);
    }

    public MenuDTO getMenu(int restaurantId) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check if the restaurant belongs to the owner
        Restaurant restaurant = restaurantRepository.findByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId,ownerId);
        if (restaurant == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }
        return mapper.toDTO(repository.findByRestaurant_RestaurantId(restaurantId));
    }


}
