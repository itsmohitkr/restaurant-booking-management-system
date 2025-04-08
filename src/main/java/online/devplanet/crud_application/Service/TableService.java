package online.devplanet.crud_application.Service;

import online.devplanet.crud_application.Config.UserAuthenticationToken;
import online.devplanet.crud_application.DTO.TableDTO;
import online.devplanet.crud_application.Mapper.TableMapper;
import online.devplanet.crud_application.Repository.RestaurantRepository;
import online.devplanet.crud_application.Repository.TableRepository;
import online.devplanet.crud_application.exception.CustomException;
import online.devplanet.crud_application.model.Tables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    private final TableRepository tableRepository;
    private final RestaurantRepository restaurantRepository;
    private final TableMapper tableMapper;

    // get the owner id from the security context
    private int getOwnerIdFromSecurityContext() {
        UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return userAuthenticationToken.getOwnerId();
    }

    @Autowired
    public TableService(TableRepository tableRepository, TableMapper tableMapper, RestaurantRepository restaurantRepository) {
        this.tableRepository = tableRepository;
        this.tableMapper = tableMapper;
        this.restaurantRepository = restaurantRepository;
    }

    public List<TableDTO> getAllTables(int restaurantId) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check if the restaurant belongs to the owner
        if (!restaurantRepository.existsByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }

        return tableMapper.toDTOList(tableRepository.findAllByRestaurant_RestaurantIdAndRestaurant_RestaurantOwner_OwnerId(restaurantId, ownerId));
    }

    public TableDTO getTableById(int id) {
        Tables table = tableRepository.findById(id).orElseThrow(()->new CustomException(HttpStatus.BAD_REQUEST, "Table not found with id: " + id));
        return tableMapper.toDTO(table);
    }

    public void addTable(TableDTO tableDTO, int restaurantId) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check if the restaurant belongs to the owner
        if(tableDTO.getRestaurantId()!= restaurantId){
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant ID in the json body not match with the restaurant ID in the path");
        }
        if (!restaurantRepository.existsByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }
        tableRepository.save(tableMapper.toEntity(tableDTO));
    }

    public void updateTable(int id, TableDTO tableDTO, int restaurantId) {
        int ownerId = getOwnerIdFromSecurityContext();
        // check if the restaurant belongs to the owner
        // check if the restaurant id in the json body is same as the restaurant id in the path
        if (tableDTO.getRestaurantId() != restaurantId) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant ID in the json body not match with the restaurant ID in the path");
        }

        if (!restaurantRepository.existsByRestaurantIdAndRestaurantOwner_OwnerId(restaurantId, ownerId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Restaurant not found with id: " + restaurantId);
        }

        Tables table = tableRepository.findByTableId(id);
        if (table == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Table not found with id: " + id);
        }
        tableDTO.setTableId(table.getTableId());
        tableRepository.save(tableMapper.toEntity(tableDTO));
    }
}
