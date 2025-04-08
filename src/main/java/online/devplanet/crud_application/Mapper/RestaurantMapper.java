package online.devplanet.crud_application.Mapper;

import online.devplanet.crud_application.DTO.RestaurantDTO;
import online.devplanet.crud_application.model.Restaurant;
import online.devplanet.crud_application.model.RestaurantOwner;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

//    @Mapping(source = "restaurantOwner.ownerId", target = "ownerId")
    RestaurantDTO toDTO(Restaurant restaurant);

//    @Mapping(source = "ownerId", target = "restaurantOwner", qualifiedByName = "ownerFromId")
    Restaurant toEntity(RestaurantDTO restaurantDTO);

    List<RestaurantDTO> toDTOList(List<Restaurant> restaurants);

//    // Custom Mapping to Convert `ownerId` → `RestaurantOwner`
//    @Named("ownerFromId")
//    default RestaurantOwner ownerFromId(int ownerId) {
//        RestaurantOwner owner = new RestaurantOwner();
//        owner.setOwnerId(ownerId);
//        return owner;
//    }
}