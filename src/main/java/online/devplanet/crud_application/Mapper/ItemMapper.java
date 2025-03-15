package online.devplanet.crud_application.Mapper;

import online.devplanet.crud_application.DTO.ItemDTO;
import online.devplanet.crud_application.model.Items;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(source = "menuId", target = "menu.menuId")
    Items toEntity(ItemDTO itemDTO);
    @Mapping(source = "menu.menuId", target = "menuId")
    ItemDTO toDTO(Items item);

    List<ItemDTO> toDTOList(List<Items> items);
}
