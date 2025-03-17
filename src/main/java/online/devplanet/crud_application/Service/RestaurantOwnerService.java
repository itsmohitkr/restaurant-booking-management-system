package online.devplanet.crud_application.Service;

import jakarta.validation.Valid;
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


    private List<RestaurantOwnerDTO> getRestaurantOwnerByNameAndEmail(String ownerName, String ownerEmail) {
        List<RestaurantOwner> restaurantOwners = restaurantOwnerRepository.findByOwnerNameAndOwnerEmail(ownerName, ownerEmail);
        if (restaurantOwners.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant Owner not found with name: " + ownerName + " and email: " + ownerEmail);
        }
        return mapper.toDTOList(restaurantOwners);
    }

    public RestaurantOwnerDTO getRestaurantOwnerById(int id) {
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST,"Restaurant Owner not found with id: " + id));
        return mapper.toDTO(restaurantOwner);
    }


    public void addRestaurantOwner(RestaurantOwnerDTO restaurantOwnerDTO) {
        // check if restaurant owner already exists
        if (restaurantOwnerRepository.existsByOwnerEmail(restaurantOwnerDTO.getOwnerEmail())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant Owner with email: " + restaurantOwnerDTO.getOwnerEmail() + " already exists");
        }

        RestaurantOwner restaurantOwner = mapper.toEntity(restaurantOwnerDTO);
        restaurantOwnerRepository.save(restaurantOwner);
    }

    public List<RestaurantOwnerDTO> getRestaurantOwnerByEmail(String ownerEmail) {
        List<RestaurantOwner> restaurantOwner = restaurantOwnerRepository.findByOwnerEmail(ownerEmail);
        if (restaurantOwner.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant Owner not found with email: " + ownerEmail);
        }
        return mapper.toDTOList(restaurantOwner);
    }

    public List<RestaurantOwnerDTO> getRestaurantOwnerByName(String ownerName) {
        List<RestaurantOwner> restaurantOwners = restaurantOwnerRepository.findByOwnerName(ownerName);
        if (restaurantOwners.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant Owner not found with name: " + ownerName);
        }
        return mapper.toDTOList(restaurantOwners);
    }

    public void updateRestaurantOwner(int id, @Valid RestaurantOwnerDTO restaurantOwnerDTO) {
        // check if id in the request body matches the id in the path
        if (id != restaurantOwnerDTO.getOwnerId()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Id in the request body does not match the id in the path");
        }
        // check if restaurant owner exists
        if (!restaurantOwnerRepository.existsById(id)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant Owner not found with id: " + id);
        }

        RestaurantOwner restaurantOwner = mapper.toEntity(restaurantOwnerDTO);
        restaurantOwner.setOwnerId(id);
        restaurantOwnerRepository.save(restaurantOwner);
    }
}
