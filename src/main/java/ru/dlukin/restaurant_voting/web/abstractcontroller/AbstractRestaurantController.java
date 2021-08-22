package ru.dlukin.restaurant_voting.web.abstractcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.service.RestaurantService;
import ru.dlukin.restaurant_voting.to.UserTo;

import java.util.List;

import static ru.dlukin.restaurant_voting.util.UserUtil.createNewFromTo;
import static ru.dlukin.restaurant_voting.util.UserUtil.prepareToSave;
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

   /* public List<Restaurant> getAllbyDate() {
        log.info("getAll");
        return service.getAll();
    }*/

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

    public Restaurant findByName(String name) {
        log.info("findByName {}", name);
        return service.findByName(name);
    }
}