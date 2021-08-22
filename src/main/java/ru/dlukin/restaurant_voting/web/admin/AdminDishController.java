package ru.dlukin.restaurant_voting.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dlukin.restaurant_voting.model.Dish;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.repository.DishRepository;
import ru.dlukin.restaurant_voting.service.DishService;
import ru.dlukin.restaurant_voting.to.UserTo;
import ru.dlukin.restaurant_voting.web.abstractcontroller.AbstractUserController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {

    static final String REST_URL = "/api/admin/restaurants";

    @Autowired
    private DishService service;

    @GetMapping(value = "{restaurantId}/dishes/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Dish get(@PathVariable("restaurantId") int restaurantId, @PathVariable("id") int id) {
        return service.findByIdAndRestaurantId(id, restaurantId);
    }

    @GetMapping(value = "{restaurantId}/dishes")
    @ResponseStatus(value = HttpStatus.OK)
    public Dish getAll(@PathVariable("restaurantId") int restaurantId, @PathVariable("id") int id) {
        return repository.findAllByIdAndRestaurantId(id, restaurantId);
    }*/

}