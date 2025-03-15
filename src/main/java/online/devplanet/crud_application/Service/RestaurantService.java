package online.devplanet.crud_application.Service;

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

        Restaurant restaurant = restaurantMapper.toEntity(restaurantDTO);
        restaurant.setRestaurantOwner(owner);

        Restaurant savedRestaurant= restaurantRepository.save(restaurant);

        owner.getRestaurants().add(restaurant);
        restaurantOwnerRepository.save(owner);
    }
}
