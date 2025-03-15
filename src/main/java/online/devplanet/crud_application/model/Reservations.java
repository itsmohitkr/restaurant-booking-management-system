package online.devplanet.crud_application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Reservations{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;
    private int userId;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String reservationDate;
    private String reservationTime;
    private int noOfPeople;
    private String reservationStatus = "booked";



    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt; // Automatically set when a record is created

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt; // Updated every time a record changes


}
