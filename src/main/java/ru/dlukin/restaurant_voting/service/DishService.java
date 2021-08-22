package ru.dlukin.restaurant_voting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.dlukin.restaurant_voting.model.Dish;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.repository.DishRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class DishService {

    private final DishRepository repository;

    public Dish create(Dish dish) {
        Assert.notNull(dish, "Dish must not be null");
        return repository.save(dish);
    }

    public Dish get(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found entity with id " + id));
    }

    public List<Dish> getAll() {
        return repository.findAll();
    }

    public void update(Dish dish) {
        Assert.notNull(dish, "Dish must not be null");
        repository.save(dish);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    List<Dish> findAllByRestaurantAndDateMenu(Restaurant restaurant, LocalDate dateVote) {
        return repository.findAllByRestaurantAndDateMenu(restaurant, dateVote);
    }

    List<Dish> findAllByRestaurant(Restaurant restaurant) {
        return repository.findAllByRestaurant(restaurant);
    }

    public Dish findByIdAndRestaurantId(int id, int restaurantId) {
        return repository.findByIdAndRestaurantId(id, restaurantId).orElseThrow(() ->
                new EntityNotFoundException("Not found entity with id " + id + " and restaurantId " + restaurantId));
    }
}
