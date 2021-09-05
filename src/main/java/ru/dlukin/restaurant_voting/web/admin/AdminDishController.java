package ru.dlukin.restaurant_voting.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dlukin.restaurant_voting.model.Dish;
import ru.dlukin.restaurant_voting.service.DishService;
import ru.dlukin.restaurant_voting.to.DishTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.DishUtil.createNewFromTo;
import static ru.dlukin.restaurant_voting.util.DishUtil.updateFromTo;
import static ru.dlukin.restaurant_voting.util.ValidationUtil.assureIdConsistent;
import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {

    public static final String REST_URL = "/api/admin/restaurants";

    @Autowired
    private DishService service;

    @GetMapping(value = "{restaurantId}/dishes/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Dish get(@PathVariable int restaurantId, @PathVariable int id) {
        return service.getByIdAndRestaurantId(id, restaurantId);
    }

    @DeleteMapping(value = "{restaurantId}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        service.delete(id, restaurantId);
    }

    @PutMapping(value = "{restaurantId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody DishTo dishTo, @PathVariable int restaurantId, @PathVariable int id) {
        assureIdConsistent(dishTo, id);
        service.update(updateFromTo(dishTo), restaurantId);
    }

    @PostMapping(value = "{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDish(@RequestBody DishTo dishTo, @PathVariable int restaurantId) {
        checkNew(dishTo);
        Dish created = service.create(createNewFromTo(dishTo), restaurantId);
        URI uriOfNewResource =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL + "/dishes/{id}")
                        .buildAndExpand(created.getId())
                        .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "{restaurantId}/dishes")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Dish> getAllByRestaurant(@PathVariable int restaurantId) {
        return service.getAllByRestaurantId(restaurantId);
    }

    @GetMapping(value = "{restaurantId}/dishes/by-date")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Dish> getAllByRestaurantAndDateVote(@PathVariable int restaurantId,
                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateVote) {
        return service.getAllByRestaurantIdAndDateVote(restaurantId, dateVote);
    }
}