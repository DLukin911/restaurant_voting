package ru.dlukin.restaurant_voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "menu")
public class UserRestaurantController {

    static final String REST_URL = "/api/user/restaurants";

    @Autowired
    RestaurantRepository repository;

    @GetMapping("/by-date")
    public List<Restaurant> getAllByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAll by Date");
        return repository.findAllByDate(date);
    }

    @Cacheable
    @GetMapping("/by-today")
    public List<Restaurant> getAllByToday() {
        log.info("getAll by Today");
        return repository.findAllByDate(LocalDate.now());
    }
}