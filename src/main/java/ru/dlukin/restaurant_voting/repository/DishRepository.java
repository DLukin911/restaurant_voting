package ru.dlukin.restaurant_voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.Dish;
import ru.dlukin.restaurant_voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    List<Dish> findAllByRestaurantAndDateMenu(Restaurant restaurant, LocalDate dateVote);

    List<Dish> findAllByRestaurant(Restaurant restaurant);

    Optional <Dish> findByIdAndRestaurantId(int id, int restaurantId);
}
