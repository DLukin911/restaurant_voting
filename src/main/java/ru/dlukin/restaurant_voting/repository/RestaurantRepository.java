package ru.dlukin.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.menuItems m WHERE m.date=:date ORDER BY r.name ASC")
    List<Restaurant> findAllByDate(LocalDate date);

    Optional<Restaurant> findByName(String name);
}
