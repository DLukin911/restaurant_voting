package ru.dlukin.restaurant_voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.Dish;

public interface DishRepository extends JpaRepository<Dish, Integer> {
}
