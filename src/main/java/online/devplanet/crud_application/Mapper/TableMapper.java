package online.devplanet.crud_application.Mapper;

import online.devplanet.crud_application.DTO.TableDTO;
import online.devplanet.crud_application.model.Tables;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TableMapper {

    Tables toEntity(TableDTO tableDTO);
    TableDTO toDTO(Tables table);
    List<TableDTO> toDTOList(List<Tables> tables);
}
