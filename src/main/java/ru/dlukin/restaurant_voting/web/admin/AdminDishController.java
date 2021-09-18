package ru.dlukin.restaurant_voting.web.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dlukin.restaurant_voting.model.Dish;
import ru.dlukin.restaurant_voting.service.DishService;
import ru.dlukin.restaurant_voting.to.DishTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.DishUtil.createNewFromTo;
import static ru.dlukin.restaurant_voting.util.DishUtil.updateFromTo;
import static ru.dlukin.restaurant_voting.util.ValidationUtil.assureIdConsistent;
import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {

    public static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    @Autowired
    private DishService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDish(@Valid @RequestBody DishTo dishTo, @PathVariable int restaurantId) {
        checkNew(dishTo);
        log.info("create with restaurantId {}", restaurantId);
        Dish created = service.create(createNewFromTo(dishTo), restaurantId);
        URI uriOfNewResource =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL + "/{id}")
                        .buildAndExpand(restaurantId, created.getId())
                        .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Transactional
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int restaurantId, @PathVariable int id) {
        assureIdConsistent(dishTo, id);
        Dish dish = updateFromTo(service.getByIdAndRestaurantId(id, restaurantId), dishTo);
        log.info("update with dishId={} and restaurantId {}", id, restaurantId);
        service.update(dish, restaurantId);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete with dishId={} and restaurantId {}", id, restaurantId);
        service.delete(id, restaurantId);
    }

    @GetMapping(value = "/{id}")
    public Dish get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get with dishId={} and restaurantId {}", id, restaurantId);
        return service.getByIdAndRestaurantId(id, restaurantId);
    }

    @GetMapping
    public List<Dish> getAllByRestaurant(@PathVariable int restaurantId) {
        log.info("get all with restaurantId {}", restaurantId);
        return service.getAllByRestaurantId(restaurantId);
    }

    @GetMapping(value = "/by-date")
    public List<Dish> getAllByRestaurantAndDate(@PathVariable int restaurantId,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get all with restaurantId {} and Date", restaurantId);
        return service.getAllByRestaurantIdAndDate(restaurantId, date);
    }
}