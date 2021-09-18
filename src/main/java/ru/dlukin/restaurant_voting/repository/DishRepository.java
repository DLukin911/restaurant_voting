package ru.dlukin.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    List<Dish> findAllByRestaurantIdAndDate(int restaurantId, LocalDate date);

    List<Dish> findAllByRestaurantId(int restaurantId);

    Optional<Dish> findByIdAndRestaurantId(int id, int restaurantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    int deleteByIdAndRestaurantId(int id, int restaurantId);
}
