package online.devplanet.crud_application.Service;

import online.devplanet.crud_application.Config.UserAuthenticationToken;
import online.devplanet.crud_application.DTO.RestaurantOwnerDTO;
import online.devplanet.crud_application.Mapper.RestaurantOwnerMapper;
import online.devplanet.crud_application.Repository.RestaurantOwnerRepository;
import online.devplanet.crud_application.exception.CustomException;
import online.devplanet.crud_application.model.RestaurantOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantOwnerService {

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    private RestaurantOwnerMapper mapper;

    // get the owner id from the security context
    private int getOwnerIdFromSecurityContext() {
        UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return userAuthenticationToken.getOwnerId();
    }


//    public List<RestaurantOwnerDTO> getAllRestaurantOwners() {
//        int ownerId = getOwnerIdFromSecurityContext();
//        return mapper.toDTOList(restaurantOwnerRepository.findAllByOwnerId(ownerId));
//    }


    private List<RestaurantOwnerDTO> getRestaurantOwnerByNameAndEmail(String ownerName, String ownerEmail) {
        List<RestaurantOwner> restaurantOwners = restaurantOwnerRepository.findByOwnerNameAndOwnerEmail(ownerName, ownerEmail);
        if (restaurantOwners.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant Owner not found with name: " + ownerName + " and email: " + ownerEmail);
        }
        return mapper.toDTOList(restaurantOwners);
    }

    public RestaurantOwnerDTO getRestaurantOwnerById(int id) {

        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findByOwnerId(id);
        if (restaurantOwner == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant Owner not found with id: " + id);
        }
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

//    public List<RestaurantOwnerDTO> getRestaurantOwnerByEmail(String ownerEmail) {
//        List<RestaurantOwner> restaurantOwner = restaurantOwnerRepository.findByOwnerEmail(ownerEmail);
//        if (restaurantOwner.isEmpty()) {
//            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant Owner not found with email: " + ownerEmail);
//        }
//        return mapper.toDTOList(restaurantOwner);
//    }

    public List<RestaurantOwnerDTO> getRestaurantOwnerByName(String ownerName) {
        List<RestaurantOwner> restaurantOwners = restaurantOwnerRepository.findByOwnerName(ownerName);
        if (restaurantOwners.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant Owner not found with name: " + ownerName);
        }
        return mapper.toDTOList(restaurantOwners);
    }

    public void updateRestaurantOwner(RestaurantOwnerDTO restaurantOwnerDTO) {
        // check if restaurant owner already exists
        int ownerId = getOwnerIdFromSecurityContext();

//        if (id != restaurantOwnerDTO.getOwnerId()) {
//            throw new CustomException(HttpStatus.BAD_REQUEST, "Id in the request body does not match the id in the path");
//        }
        // check if restaurant owner exists
        if (!restaurantOwnerRepository.existsById(ownerId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant Owner not found with id: " + ownerId);
        }
        RestaurantOwner restaurantOwner = mapper.toEntity(restaurantOwnerDTO);
        restaurantOwner.setOwnerId(ownerId);
        restaurantOwnerRepository.save(restaurantOwner);
    }

    public RestaurantOwnerDTO getRestaurantOwnerByOwnerEmail(String ownerEmail) {
        RestaurantOwner restaurantOwners = restaurantOwnerRepository.findByOwnerEmail(ownerEmail);
        if (restaurantOwners == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant Owner not found with email: " + ownerEmail);
        }

        return mapper.toDTO(restaurantOwners);
    }

    public RestaurantOwnerDTO getRestaurantOwner() {
        int ownerId = getOwnerIdFromSecurityContext();
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findByOwnerId(ownerId);
        if (restaurantOwner == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant Owner not found with id: " + ownerId);
        }
        return mapper.toDTO(restaurantOwner);
    }
}
