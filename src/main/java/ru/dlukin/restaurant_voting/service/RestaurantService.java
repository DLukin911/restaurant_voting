package ru.dlukin.restaurant_voting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.ValidationUtil.*;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must not be null");
        return repository.save(restaurant);
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must not be null");
        repository.save(restaurant);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public Restaurant get(int id) {
        return checkNotFoundOptional(repository.findById(id), "id = " + id);
    }

    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    public Restaurant getByName(String name) {
        return checkNotFoundOptional(repository.findByName(name), "name = " + name);
    }

    public List<Restaurant> getAllByDate(LocalDate dateVote) {
        return repository.findAllByDateVote(dateVote);
    }

    public List<Restaurant> getAllByToday() {
        return repository.findAllByDateVote(LocalDate.now());
    }
}
