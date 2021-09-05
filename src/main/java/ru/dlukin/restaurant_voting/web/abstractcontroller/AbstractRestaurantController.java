package ru.dlukin.restaurant_voting.web.abstractcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.ValidationUtil.assureIdConsistent;
import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNew;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    private RestaurantService service;

    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public List<Restaurant> getAllByDate(LocalDate dateVote) {
        log.info("getAll by Date");
        return service.getAllByDate(dateVote);
    }

    public List<Restaurant> getAllByToday() {
        log.info("getAll by Today");
        return service.getAllByToday();
    }

    public Restaurant get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return service.create(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }

    public Restaurant getByName(String name) {
        log.info("getByName {}", name);
        return service.getByName(name);
    }
}