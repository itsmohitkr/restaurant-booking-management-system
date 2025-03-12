package online.devplanet.crud_application.Repository;


import online.devplanet.crud_application.model.Tables;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Tables, Integer> {
}
