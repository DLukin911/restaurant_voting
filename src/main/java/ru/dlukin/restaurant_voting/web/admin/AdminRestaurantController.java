package ru.dlukin.restaurant_voting.web.admin;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.to.RestaurantTo;
import ru.dlukin.restaurant_voting.util.View;
import ru.dlukin.restaurant_voting.web.abstractcontroller.AbstractRestaurantController;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "menu")
public class AdminRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL = "/api/admin/restaurants";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody RestaurantTo restaurantTo) {
        Restaurant created = super.create(restaurantTo);
        URI uriOfNewResource =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL + "/{id}")
                        .buildAndExpand(created.getId())
                        .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        super.update(restaurantTo, id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping("/{id}")
    @JsonView(View.REST.class)
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    @JsonView(View.REST.class)
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/by-name")
    public Restaurant getByName(@RequestParam String name) {
        return super.getByName(name);
    }

    @Override
    @GetMapping("/by-date")
    public List<Restaurant> getAllByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getAllByDate(date);
    }

    @Override
    @Cacheable
    @GetMapping("/by-today")
    public List<Restaurant> getAllByToday() {
        return super.getAllByToday();
    }
}