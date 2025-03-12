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
public class ReservationService {

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private ReservationMapper reservationMapper;


    public List<ReservationDTO> getAllReservation() {

        return reservationMapper.toDTOList(repository.findAll());
    }

    public void addReservation(ReservationDTO reservationDTO) {
        Reservations reservation = reservationMapper.toEntity(reservationDTO);
        repository.save(reservation);
    }

    public ReservationDTO getReservationById(int id) {

        Reservations reservation = repository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Reservation not found with id: " + id));
        return reservationMapper.toDTO(reservation);
    }

    public void updateReservationStatus(ReservationDTO reservationDTO) {
        repository.save(reservationMapper.toEntity(reservationDTO));
    }

    public void updateReservation(ReservationDTO reservationDTO) {
        addReservation(reservationDTO);
    }

    public List<ReservationDTO> getReservationByMobileNo(String mobileNo) {
        List<Reservations> reservation = repository.findByMobileNo(mobileNo);
        if (reservation.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Reservation not found with mobile number: " + mobileNo);
        }

        return reservationMapper.toDTOList(reservation);
    }

    public List<ReservationDTO> getReservationByReservationDate(String date) {
        List<Reservations> reservation = repository.findByReservationDate(date);
        if (reservation.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Reservation not found with reservation date: " + date);
        }

        return reservationMapper.toDTOList(reservation);
    }

    public List<ReservationDTO> getReservationByFirstNameAndLastName(String firstName, String lastName) {
        List<Reservations> reservation = repository.findByFirstNameAndLastName(firstName, lastName);
        if (reservation.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Reservation not found with first name: " + firstName + " and last name: " + lastName);
        }

        return reservationMapper.toDTOList(reservation);
    }
}
