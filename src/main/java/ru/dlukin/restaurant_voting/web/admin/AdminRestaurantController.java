package ru.dlukin.restaurant_voting.web.admin;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.to.RestaurantTo;
import ru.dlukin.restaurant_voting.web.abstractcontroller.AbstractRestaurantController;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.RestaurantUtil.createNewFromTo;
import static ru.dlukin.restaurant_voting.util.RestaurantUtil.updateFromTo;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/admin/restaurants";

    @Override
    @GetMapping("/by-date")
    public List<Restaurant> getAllByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateVote) {
        return super.getAllByDate(dateVote);
    }

    @Override
    @GetMapping("/by-today")
    public List<Restaurant> getAllByToday() {
        return super.getAllByToday();
    }

    @Override
    @GetMapping
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody RestaurantTo restaurantTo) {
        Restaurant created = super.create(createNewFromTo(restaurantTo));
        URI uriOfNewResource =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL + "/{id}")
                        .buildAndExpand(created.getId())
                        .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        super.update(updateFromTo(restaurantTo), id);
    }


    @Override
    @GetMapping("/by-name")
    public Restaurant getByName(@RequestParam String name) {
        return super.getByName(name);
    }
}