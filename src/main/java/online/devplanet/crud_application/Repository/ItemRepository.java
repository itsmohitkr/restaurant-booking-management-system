package online.devplanet.crud_application.Repository;

import online.devplanet.crud_application.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import java.lang.ScopedValue;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Items, Integer> {
//    List<Items> findByMenu_MenuId(int menuId);

    List<Items> findByMenu_MenuIdAndItemNameContainingOrItemDescriptionContaining(int menuId, String keyword, String keyword1);

//    Collection<Object> findByIdAndMenu_MenuId(int itemId, int menuId);

    List<Items> findAllByMenu_MenuId(int menuId);

    List<Items> findAllByMenu_MenuIdAndItemNameContainingOrItemDescriptionContaining(int menuId, String keyword, String keyword1);

//    Items findByIdAndMenu_MenuId(int itemId, int menuId);

    Items findByItemIdAndMenu_MenuId(int itemId, int menuId);
}
