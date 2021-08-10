package ru.dlukin.restaurant_voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.Restaurant;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    Optional<Restaurant> findByName(String name);
}
