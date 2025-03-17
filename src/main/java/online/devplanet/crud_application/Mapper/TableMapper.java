package online.devplanet.crud_application.Mapper;

import online.devplanet.crud_application.DTO.TableDTO;
import online.devplanet.crud_application.model.Tables;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TableMapper {

    @Mapping(source = "restaurantId", target = "restaurant.restaurantId")
    Tables toEntity(TableDTO tableDTO);
    @Mapping(source = "restaurant.restaurantId", target = "restaurantId")
    TableDTO toDTO(Tables table);
    List<TableDTO> toDTOList(List<Tables> tables);
}
