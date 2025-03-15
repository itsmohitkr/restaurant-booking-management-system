package online.devplanet.crud_application.Mapper;

import online.devplanet.crud_application.DTO.MenuDTO;
import online.devplanet.crud_application.model.Menu;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    Menu toEntity(MenuDTO menuDTO);
    MenuDTO toDTO(Menu menu);
    List<MenuDTO> toDTOList(List<Menu> menus);

}
