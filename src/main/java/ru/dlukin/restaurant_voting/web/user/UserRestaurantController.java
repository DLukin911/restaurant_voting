package ru.dlukin.restaurant_voting.web.user;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.util.View;
import ru.dlukin.restaurant_voting.web.abstractcontroller.AbstractRestaurantController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/user/restaurants";

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
    @GetMapping("/by-today")
    public List<Restaurant> getAllByToday() {
        return super.getAllByToday();
    }
}