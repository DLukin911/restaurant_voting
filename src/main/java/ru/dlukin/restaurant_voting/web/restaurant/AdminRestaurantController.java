package ru.dlukin.restaurant_voting.web.restaurant;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.repository.RestaurantRepository;
import ru.dlukin.restaurant_voting.to.RestaurantTo;
import ru.dlukin.restaurant_voting.util.View;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.RestaurantUtil.createNewFromTo;
import static ru.dlukin.restaurant_voting.util.RestaurantUtil.updateFromTo;
import static ru.dlukin.restaurant_voting.util.ValidationUtil.*;

@Slf4j
@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    public static final String REST_URL = "/api/admin/restaurants";

    @Autowired
    RestaurantRepository repository;

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "menu", allEntries = true)
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody RestaurantTo restaurantTo) {
        checkNew(restaurantTo);
        log.info("create {}", restaurantTo);
        Restaurant created = repository.save(createNewFromTo(restaurantTo));
        URI uriOfNewResource =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL + "/{id}")
                        .buildAndExpand(created.getId())
                        .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "menu", allEntries = true)
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        assureIdConsistent(restaurantTo, id);
        log.info("update {} with id={}", restaurantTo, id);
        repository.save(updateFromTo(checkNotFoundOptional(repository.findById(id), "id = " + id), restaurantTo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "menu", allEntries = true)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @GetMapping("/{id}")
    @JsonView(View.REST.class)
    public Restaurant get(@PathVariable int id) {
        log.info("get {}", id);
        return checkNotFoundOptional(repository.findById(id), "id = " + id);
    }

    @GetMapping
    @JsonView(View.REST.class)
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @GetMapping("/by-name")
    public Restaurant getByName(@RequestParam String name) {
        log.info("getByName {}", name);
        return checkNotFoundOptional(repository.findByName(name), "name = " + name);
    }
}