package online.devplanet.crud_application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "items")
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;
    private String itemName;
    private String itemDescription;
    private double itemPrice;

    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "menuId")
    private Menu menu;

}
