package online.devplanet.crud_application.Service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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

    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurantMapper.toDTOList(restaurants);
    }


    public RestaurantDTO getRestaurantById(int id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST,"Restaurant not found with id: " + id));
        return restaurantMapper.toDTO(restaurant);
    }

    @Transactional
    public void addRestaurant(RestaurantDTO restaurantDTO) {
        RestaurantOwner owner = restaurantOwnerRepository.findById(restaurantDTO.getOwnerId())
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST,"Restaurant Owner not found with id: " + restaurantDTO.getOwnerId()));

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
        List<Restaurant> restaurants = restaurantRepository.findByRestaurantNameContainingOrRestaurantContactContainingOrRestaurantEmailContainingOrRestaurantLocationContaining(keyword, keyword, keyword, keyword);
        return restaurantMapper.toDTOList(restaurants);

    }

    public List<RestaurantDTO> getRestaurantByName(String restaurantName) {

        List<Restaurant> restaurants = restaurantRepository.findByRestaurantName(restaurantName);
        return restaurantMapper.toDTOList(restaurants);
    }

    public RestaurantDTO getRestaurantByEmail(String restaurantEmail) {
        Restaurant restaurant =  restaurantRepository.findByRestaurantEmail(restaurantEmail);
        return restaurantMapper.toDTO(restaurant);
    }

    public RestaurantDTO getRestaurantByPhone(String restaurantPhone) {
        Restaurant restaurant =  restaurantRepository.findByRestaurantContact(restaurantPhone);
        return restaurantMapper.toDTO(restaurant);
    }

    public void updateRestaurant(int id, @Valid RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST,"Restaurant not found with id: " + id));

        // check if the restaurant already exists by email
        if (!restaurant.getRestaurantEmail().equals(restaurantDTO.getRestaurantEmail()) && restaurantRepository.existsByRestaurantEmail(restaurantDTO.getRestaurantEmail())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant already exists with email: " + restaurantDTO.getRestaurantEmail());
        }
        // check if the restaurant already exists by phone
        if (!restaurant.getRestaurantContact().equals(restaurantDTO.getRestaurantContact()) && restaurantRepository.existsByRestaurantContact(restaurantDTO.getRestaurantContact())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant already exists with phone: " + restaurantDTO.getRestaurantContact());
        }

        // convert DTO to Entity
        Restaurant updatedRestaurant = restaurantMapper.toEntity(restaurantDTO);
        updatedRestaurant.setRestaurantId(restaurant.getRestaurantId());
        restaurantRepository.save(updatedRestaurant);
    }

    public void deleteRestaurant(int id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST,"Restaurant not found with id: " + id));
        restaurantRepository.delete(restaurant);
    }
}
