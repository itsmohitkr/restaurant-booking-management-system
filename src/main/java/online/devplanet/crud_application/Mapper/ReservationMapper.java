package online.devplanet.crud_application.Mapper;

import online.devplanet.crud_application.DTO.ReservationDTO;
import online.devplanet.crud_application.model.Reservations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "reservationDate", source = "reservationDate", qualifiedByName = "localDateToString")
    @Mapping(source = "restaurantId", target = "restaurant.restaurantId")
    Reservations toEntity(ReservationDTO reservationDTO);

    @Mapping(target = "reservationDate", source = "reservationDate", qualifiedByName = "stringToLocalDate")
    @Mapping(source = "restaurant.restaurantId", target = "restaurantId")
    ReservationDTO toDTO(Reservations reservations);

    @Named("stringToLocalDate")
    static LocalDate stringToLocalDate(String date) {
        return date == null ? null : LocalDate.parse(date);
    }

    @Named("localDateToString")
    static String localDateToString(LocalDate date) {
        return date == null ? null : date.toString();
    }

    List<ReservationDTO> toDTOList(List<Reservations> all);
}