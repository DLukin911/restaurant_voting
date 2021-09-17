package ru.dlukin.restaurant_voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.dlukin.restaurant_voting.testdata.RestaurantTestData.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void create() {
        Restaurant created = service.create(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        service.update(updated);
        RESTAURANT_MATCHER.assertMatch(service.get(KFC_ID), getUpdated());
    }

    @Test
    void delete() {
        service.delete(KFC_ID);
        assertThrows(NotFoundException.class, () -> service.get(KFC_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID));
    }

    @Test
    void get() {
        Restaurant restaurantFromDatabase = service.get(KFC_ID);
        RESTAURANT_MATCHER.assertMatch(restaurantFromDatabase, kfc);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_ID));
    }

    @Test
    void getAll() {
        List<Restaurant> all = service.getAll();
        RESTAURANT_MATCHER.assertMatch(all, kfc, mcDonalds, burgerKing);
    }

    @Test
    void getByName() {
        Restaurant restaurantFromDatabase = service.getByName("KFC");
        RESTAURANT_MATCHER.assertMatch(restaurantFromDatabase, kfc);
    }

    @Test
    void getAllByDate() {
        List<Restaurant> restaurantsFromDatabase = service.getAllByDate(LocalDate.of(2021, 9, 1));
        RESTAURANT_MATCHER.assertMatch(restaurantsFromDatabase, List.of(burgerKing));
    }

    @Test
    void getAllByToday() {
        List<Restaurant> restaurantsFromDatabase = service.getAllByToday();
        RESTAURANT_MATCHER.assertMatch(restaurantsFromDatabase, List.of(kfc, mcDonalds));
    }

    @Test
    void createWithException() {
        assertThrows(ConstraintViolationException.class, () -> service.create(new Restaurant(null, "  ", null)));
        assertThrows(DataIntegrityViolationException.class, () -> service.create(new Restaurant(null, kfc.getName(), null)));
    }
}