package ru.dlukin.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.MenuItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {

    List<MenuItem> findAllByRestaurantIdAndDate(int restaurantId, LocalDate date);

    List<MenuItem> findAllByRestaurantId(int restaurantId);

    Optional<MenuItem> findByIdAndRestaurantId(int id, int restaurantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM MenuItem m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int deleteByIdAndRestaurantId(int id, int restaurantId);
}
