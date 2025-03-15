package online.devplanet.crud_application.Mapper;

import online.devplanet.crud_application.DTO.ItemDTO;
import online.devplanet.crud_application.model.Items;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Items toEntity(ItemDTO itemDTO);
    ItemDTO toDTO(Items item);
    List<ItemDTO> toDTOList(List<Items> items);
}
