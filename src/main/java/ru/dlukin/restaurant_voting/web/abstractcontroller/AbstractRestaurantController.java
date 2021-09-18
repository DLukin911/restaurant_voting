package ru.dlukin.restaurant_voting.web.abstractcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.service.RestaurantService;
import ru.dlukin.restaurant_voting.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.RestaurantUtil.*;
import static ru.dlukin.restaurant_voting.util.ValidationUtil.assureIdConsistent;
import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNew;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    private RestaurantService service;

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        return service.create(restaurant);
    }

    public Restaurant create(RestaurantTo restaurantTo) {
        checkNew(restaurantTo);
        Restaurant restaurant = createNewFromTo(restaurantTo);
        return service.create(restaurant);
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update {} with id={}", restaurant, id);
        service.update(restaurant);
    }

    @Transactional
    public void update(RestaurantTo restaurantTo, int id) {
        assureIdConsistent(restaurantTo, id);
        Restaurant restaurant = updateFromTo(service.get(id), restaurantTo);
        service.update(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public Restaurant get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Restaurant getByName(String name) {
        log.info("getByName {}", name);
        return service.getByName(name);
    }

    public List<Restaurant> getAllByDate(LocalDate dateVote) {
        log.info("getAll by Date");
        return service.getAllByDate(dateVote);
    }

    public List<Restaurant> getAllByToday() {
        log.info("getAll by Today");
        return service.getAllByToday();
    }
}