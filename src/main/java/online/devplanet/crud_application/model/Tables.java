package online.devplanet.crud_application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tableId;
    private int userId;
    private String tableName;
    private int capacity;
    private int reservationId=0;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt; // Automatically set when a record is created

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt; // Updated every time a record changes

}
