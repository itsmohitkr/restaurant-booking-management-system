package online.devplanet.crud_application.Service;

import online.devplanet.crud_application.DTO.TableDTO;
import online.devplanet.crud_application.Mapper.TableMapper;
import online.devplanet.crud_application.Repository.TableRepository;
import online.devplanet.crud_application.exception.CustomException;
import online.devplanet.crud_application.model.Tables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    private final TableRepository tableRepository;
    private final TableMapper tableMapper;

    @Autowired
    public TableService(TableRepository tableRepository, TableMapper tableMapper) {
        this.tableRepository = tableRepository;
        this.tableMapper = tableMapper;
    }

    public List<TableDTO> getAllTables() {
        return tableMapper.toDTOList(tableRepository.findAll());
    }

    public TableDTO getTableById(int id) {
        Tables table = tableRepository.findById(id).orElseThrow(()->new CustomException(HttpStatus.BAD_REQUEST, "Table not found with id: " + id));
        return tableMapper.toDTO(table);
    }

    public void addTable(TableDTO tableDTO) {

        tableRepository.save(tableMapper.toEntity(tableDTO));
    }

    public void updateTable(int id, TableDTO tableDTO) {
            Tables table = tableRepository.findById(id).orElseThrow(()->new CustomException(HttpStatus.BAD_REQUEST, "Table not found with id: " + id));
            tableRepository.save(tableMapper.toEntity(tableDTO));
    }
}
