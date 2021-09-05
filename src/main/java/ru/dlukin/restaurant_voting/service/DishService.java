package ru.dlukin.restaurant_voting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.dlukin.restaurant_voting.model.Dish;
import ru.dlukin.restaurant_voting.repository.DishRepository;
import ru.dlukin.restaurant_voting.repository.RestaurantRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNotFoundWithIdAndRestaurantId;

@Service
@AllArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    private final RestaurantRepository restaurantRepository;

    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "Dish must not be null");
        dish.setRestaurant(restaurantRepository.findById(restaurantId).orElseThrow(() ->
                new IllegalArgumentException("Not found restaurant with id " + restaurantId)));
        return dishRepository.save(dish);
    }

    public Dish get(int id) {
        return dishRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found entity with id " + id));
    }

    public List<Dish> getAllByRestaurantId(int restaurantId) {
        return dishRepository.findAllByRestaurantId(restaurantId);
    }

    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "Dish must not be null");
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        dishRepository.save(dish);
    }

    public void delete(int id, int restaurantId) {
        checkNotFoundWithIdAndRestaurantId(dishRepository.deleteByIdAndRestaurantId(id, restaurantId) != 0, id,
                restaurantId);
    }

    public List<Dish> getAllByRestaurantIdAndDateVote(int restaurantId, LocalDate dateVote) {
        return dishRepository.findAllByRestaurantIdAndDateVote(restaurantId, dateVote);
    }

    public Dish getByIdAndRestaurantId(int id, int restaurantId) {
        return dishRepository.findByIdAndRestaurantId(id, restaurantId).orElseThrow(() ->
                new EntityNotFoundException("Not found entity with id " + id + " and restaurantId " + restaurantId));
    }
}
