package online.devplanet.crud_application.Service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import online.devplanet.crud_application.Config.UserAuthenticationToken;
import online.devplanet.crud_application.DTO.RestaurantDTO;
import online.devplanet.crud_application.Mapper.RestaurantMapper;
import online.devplanet.crud_application.Repository.RestaurantOwnerRepository;
import online.devplanet.crud_application.Repository.RestaurantRepository;
import online.devplanet.crud_application.exception.CustomException;
import online.devplanet.crud_application.model.Restaurant;
import online.devplanet.crud_application.model.RestaurantOwner;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private RestaurantOwnerRepository  restaurantOwnerRepository;


    private int getOwnerIdFromSecurityContext() {
        UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return userAuthenticationToken.getOwnerId();
    }

    public List<RestaurantDTO> getAllRestaurants() {
        int ownerId = getOwnerIdFromSecurityContext();

        List<Restaurant> restaurants = restaurantRepository.findAllByRestaurantOwner_OwnerId(ownerId);
        return restaurantMapper.toDTOList(restaurants);
    }


    public RestaurantDTO getRestaurantById(int id) {
        int ownerId = getOwnerIdFromSecurityContext();
        Restaurant restaurant = restaurantRepository.findByRestaurantIdAndRestaurantOwner_OwnerId(id,ownerId);
        if (restaurant == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Restaurant not found with id: " + id);
        }

        return restaurantMapper.toDTO(restaurant);
    }

    @Transactional
    public void addRestaurant(RestaurantDTO restaurantDTO) {

        int ownerId = getOwnerIdFromSecurityContext();

        RestaurantOwner owner = restaurantOwnerRepository.findByOwnerId(ownerId);
        if (owner == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Restaurant Owner not found with id: " + ownerId);
        }
        // check if the restaurant already exists by email
        if (restaurantRepository.existsByRestaurantEmail(restaurantDTO.getRestaurantEmail())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant already exists with email: " + restaurantDTO.getRestaurantEmail());
        }

        // check if the restaurant already exists by phone
        if (restaurantRepository.existsByRestaurantContact(restaurantDTO.getRestaurantContact())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant already exists with phone: " + restaurantDTO.getRestaurantContact());
        }

        Restaurant restaurant = restaurantMapper.toEntity(restaurantDTO);
        restaurant.setRestaurantOwner(owner);

        Restaurant savedRestaurant= restaurantRepository.save(restaurant);

        owner.getRestaurants().add(restaurant);
        restaurantOwnerRepository.save(owner);
    }

    public List<RestaurantDTO> searchRestaurant(String keyword) {
        int ownerId = getOwnerIdFromSecurityContext();
        List<Restaurant> restaurants = restaurantRepository.findByRestaurantNameContainingOrRestaurantContactContainingOrRestaurantEmailContainingOrRestaurantLocationContainingAndRestaurantOwner_OwnerId(keyword, keyword, keyword, keyword,ownerId);
        return restaurantMapper.toDTOList(restaurants);

    }

    public List<RestaurantDTO> getRestaurantByName(String restaurantName) {
        int ownerId = getOwnerIdFromSecurityContext();
        List<Restaurant> restaurants = restaurantRepository.findByRestaurantNameAndRestaurantOwner_OwnerId(restaurantName,ownerId);
        return restaurantMapper.toDTOList(restaurants);
    }

    public RestaurantDTO getRestaurantByEmail(String restaurantEmail) {
        int ownerId = getOwnerIdFromSecurityContext();
        Restaurant restaurant =  restaurantRepository.findByRestaurantEmailAndRestaurantOwner_OwnerId(restaurantEmail,ownerId);
        return restaurantMapper.toDTO(restaurant);
    }

    public RestaurantDTO getRestaurantByPhone(String restaurantPhone) {
        int ownerId = getOwnerIdFromSecurityContext();
        Restaurant restaurant =  restaurantRepository.findByRestaurantContactAndRestaurantOwner_OwnerId(restaurantPhone,ownerId);
        return restaurantMapper.toDTO(restaurant);
    }

    public void updateRestaurant(int id, @Valid RestaurantDTO restaurantDTO) {
        int ownerId = getOwnerIdFromSecurityContext();
        Restaurant restaurant = restaurantRepository.findByRestaurantIdAndRestaurantOwner_OwnerId(id,ownerId);
        if (restaurant == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Restaurant not found with id: " + id);
        }

        // check if the restaurant already exists by email
        if (!restaurant.getRestaurantEmail().equals(restaurantDTO.getRestaurantEmail()) && restaurantRepository.existsByRestaurantEmailAndRestaurantOwner_OwnerId(restaurantDTO.getRestaurantEmail(),ownerId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant already exists with email: " + restaurantDTO.getRestaurantEmail());
        }
        // check if the restaurant already exists by phone
        if (!restaurant.getRestaurantContact().equals(restaurantDTO.getRestaurantContact()) && restaurantRepository.existsByRestaurantContactAndRestaurantOwner_OwnerId(restaurantDTO.getRestaurantContact(),ownerId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant already exists with phone: " + restaurantDTO.getRestaurantContact());
        }

        // convert DTO to Entity
        Restaurant updatedRestaurant = restaurantMapper.toEntity(restaurantDTO);
        updatedRestaurant.setRestaurantId(restaurant.getRestaurantId());
        updatedRestaurant.setRestaurantOwner(restaurant.getRestaurantOwner());
        restaurantRepository.save(updatedRestaurant);
    }

    public void deleteRestaurant(int id) {
        int ownerId = getOwnerIdFromSecurityContext();
        Restaurant restaurant = restaurantRepository.findByRestaurantIdAndRestaurantOwner_OwnerId(id,ownerId);
        if (restaurant == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Restaurant not found with id: " + id);
        }
        restaurantRepository.delete(restaurant);
    }
}
