package ru.dlukin.restaurant_voting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.repository.RestaurantRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must not be null");
        return repository.save(restaurant);
    }

    public Restaurant get(int id) {
        return repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found entity with id " + id));
    }

    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must not be null");
        repository.save(restaurant);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public Restaurant getByName(String name) {
        return repository.findByName(name).orElseThrow(() ->
                new EntityNotFoundException("Not found entity with name " + name));
    }

    public List<Restaurant> getAllByDate(LocalDate dateVote) {
        return repository.findAllByDateVote(dateVote);
    }

    public List<Restaurant> getAllByToday() {
        return repository.findAllByDateVote(LocalDate.now());
    }
}
