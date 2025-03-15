package online.devplanet.crud_application.Service;

import jakarta.validation.Valid;
import online.devplanet.crud_application.DTO.MenuDTO;
import online.devplanet.crud_application.Mapper.MenuMapper;
import online.devplanet.crud_application.Repository.MenuRepository;
import online.devplanet.crud_application.Repository.RestaurantRepository;
import online.devplanet.crud_application.exception.CustomException;
import online.devplanet.crud_application.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuRepository repository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuMapper mapper;

    public void addMenu(@Valid MenuDTO menuDTO) {
        // check id restaurant exists
        Restaurant restaurant = restaurantRepository.findById(menuDTO.getRestaurantId())
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + menuDTO.getRestaurantId()));
        repository.save(mapper.toEntity(menuDTO));

    }

    public MenuDTO getMenu(int restaurantId) {
        return mapper.toDTO(repository.findByRestaurant_RestaurantId(restaurantId));
    }

    public ResponseEntity<List<MenuDTO>> getAllMenu() {
        List<MenuDTO> menus = mapper.toDTOList(repository.findAll());
        return ResponseEntity.ok(menus);
    }
}
