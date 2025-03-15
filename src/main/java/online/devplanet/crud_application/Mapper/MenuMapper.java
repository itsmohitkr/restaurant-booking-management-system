package online.devplanet.crud_application.Mapper;

import online.devplanet.crud_application.DTO.MenuDTO;
import online.devplanet.crud_application.model.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    @Mapping(source = "restaurantId", target = "restaurant.restaurantId")
    Menu toEntity(MenuDTO menuDTO);

    @Mapping(source = "restaurant.restaurantId", target = "restaurantId")
    MenuDTO toDTO(Menu menu);

    List<MenuDTO> toDTOList(List<Menu> menus);
}