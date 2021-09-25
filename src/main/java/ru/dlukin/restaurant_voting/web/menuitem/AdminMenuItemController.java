package ru.dlukin.restaurant_voting.web.menuitem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dlukin.restaurant_voting.model.MenuItem;
import ru.dlukin.restaurant_voting.service.MenuItemService;
import ru.dlukin.restaurant_voting.to.MenuItemTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.MenuItemUtil.createNewFromTo;
import static ru.dlukin.restaurant_voting.util.MenuItemUtil.updateFromTo;
import static ru.dlukin.restaurant_voting.util.ValidationUtil.assureIdConsistent;
import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = AdminMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuItemController {

    public static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menu-items";

    @Autowired
    private MenuItemService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "menu", allEntries = true)
    public ResponseEntity<MenuItem> createMenuItem(@Valid @RequestBody MenuItemTo menuItemTo, @PathVariable int restaurantId) {
        checkNew(menuItemTo);
        log.info("create with restaurantId {}", restaurantId);
        MenuItem created = service.create(createNewFromTo(menuItemTo), restaurantId);
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
    @CacheEvict(value = "menu", allEntries = true)
    public void update(@Valid @RequestBody MenuItemTo menuItemTo, @PathVariable int restaurantId, @PathVariable int id) {
        assureIdConsistent(menuItemTo, id);
        MenuItem menuItem = updateFromTo(service.getByIdAndRestaurantId(id, restaurantId), menuItemTo);
        log.info("update with dishId={} and restaurantId {}", id, restaurantId);
        service.update(menuItem, restaurantId);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "menu", allEntries = true)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete with dishId={} and restaurantId {}", id, restaurantId);
        service.delete(id, restaurantId);
    }

    @GetMapping(value = "/{id}")
    public MenuItem get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get with dishId={} and restaurantId {}", id, restaurantId);
        return service.getByIdAndRestaurantId(id, restaurantId);
    }

    @GetMapping
    public List<MenuItem> getAllByRestaurant(@PathVariable int restaurantId) {
        log.info("get all with restaurantId {}", restaurantId);
        return service.getAllByRestaurantId(restaurantId);
    }

    @GetMapping(value = "/by-date")
    public List<MenuItem> getAllByRestaurantAndDate(@PathVariable int restaurantId,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get all with restaurantId {} and Date", restaurantId);
        return service.getAllByRestaurantIdAndDate(restaurantId, date);
    }
}