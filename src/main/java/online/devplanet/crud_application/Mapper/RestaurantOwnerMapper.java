package online.devplanet.crud_application.Mapper;

import online.devplanet.crud_application.DTO.RestaurantDTO;
import online.devplanet.crud_application.DTO.RestaurantOwnerDTO;
import online.devplanet.crud_application.model.Restaurant;
import online.devplanet.crud_application.model.RestaurantOwner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = RestaurantMapper.class) // Uses RestaurantMapper for nested mapping
public interface RestaurantOwnerMapper {

    @Mapping(source = "restaurants", target = "restaurants", qualifiedByName = "mapRestaurants")
    RestaurantOwnerDTO toDTO(RestaurantOwner restaurantOwner);

    RestaurantOwner toEntity(RestaurantOwnerDTO restaurantOwnerDTO);

    List<RestaurantOwnerDTO> toDTOList(List<RestaurantOwner> restaurantOwners);

    // Custom Mapping to Convert List<Restaurant> to List<RestaurantDTO>
    @Named("mapRestaurants")
    default List<RestaurantDTO> mapRestaurants(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(restaurant -> {
                    RestaurantDTO dto = new RestaurantDTO();
                    dto.setRestaurantId(restaurant.getRestaurantId());
                    dto.setRestaurantName(restaurant.getRestaurantName());
                    dto.setRestaurantContact(restaurant.getRestaurantContact());
                    dto.setRestaurantEmail(restaurant.getRestaurantEmail());
                    dto.setRestaurantLocation(restaurant.getRestaurantLocation());
//                    dto.setOwnerId(restaurant.getRestaurantOwner().getOwnerId());
                    dto.setRestaurantAddress(restaurant.getRestaurantAddress());
                    return dto;
                })
                .toList();
    }
}