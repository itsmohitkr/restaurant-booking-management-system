package online.devplanet.crud_application.Service;

import online.devplanet.crud_application.DTO.ReservationDTO;
import online.devplanet.crud_application.Mapper.ReservationMapper;
import online.devplanet.crud_application.Repository.ReservationRepository;
import online.devplanet.crud_application.exception.CustomException;
import online.devplanet.crud_application.model.Reservations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationMapper reservationMapper;


    public List<ReservationDTO> search(String mobileNo) {
        List<Reservations> reservation = reservationRepository.findByMobileNo(mobileNo);
        if (reservation.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Reservation not found with mobile number: " + mobileNo);
        }

        return reservationMapper.toDTOList(reservation);
    }
}
