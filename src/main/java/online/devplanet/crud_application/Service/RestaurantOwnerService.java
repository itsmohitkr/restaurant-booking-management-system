package online.devplanet.crud_application.Service;

import online.devplanet.crud_application.DTO.RestaurantOwnerDTO;
import online.devplanet.crud_application.Mapper.RestaurantOwnerMapper;
import online.devplanet.crud_application.Repository.RestaurantOwnerRepository;
import online.devplanet.crud_application.exception.CustomException;
import online.devplanet.crud_application.model.RestaurantOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantOwnerService {

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    private RestaurantOwnerMapper mapper;


    public List<RestaurantOwnerDTO> getAllRestaurantOwners() {
        return mapper.toDTOList(restaurantOwnerRepository.findAll());
    }

    public RestaurantOwnerDTO getRestaurantOwnerById(int id) {
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST,"Restaurant Owner not found with id: " + id));
        return mapper.toDTO(restaurantOwner);
    }


    public void addRestaurantOwner(RestaurantOwnerDTO restaurantOwnerDTO) {
        RestaurantOwner restaurantOwner = mapper.toEntity(restaurantOwnerDTO);
        restaurantOwnerRepository.save(restaurantOwner);
    }
}
