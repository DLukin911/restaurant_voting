package ru.dlukin.restaurant_voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.util.exception.NotFoundException;

import javax.persistence.EntityNotFoundException;
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
        MATCHER.assertMatch(created, newRestaurant);
        MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void get() {
        Restaurant restaurantFromDatabase = service.get(KFC_ID);
        MATCHER.assertMatch(restaurantFromDatabase, kfc);
    }

    @Test
    void getNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.get(NOT_FOUND_ID));
    }

    @Test
    void getAll() {
        List<Restaurant> all = service.getAll();
        MATCHER.assertMatch(all, kfc, mcDonalds, burgerKing);
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        service.update(updated);
        MATCHER.assertMatch(service.get(KFC_ID), getUpdated());
    }

    @Test
    void delete() {
        service.delete(KFC_ID);
        assertThrows(EntityNotFoundException.class, () -> service.get(KFC_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID));
    }

    @Test
    void getByName() {
        Restaurant restaurantFromDatabase = service.getByName("KFC");
        MATCHER.assertMatch(restaurantFromDatabase, kfc);
    }

    @Test
    void getAllByToday() {
        List<Restaurant> restaurantsFromDatabase = service.getAllByToday();
        MATCHER.assertMatch(restaurantsFromDatabase, List.of(kfc, mcDonalds));
    }

    @Test
    void getAllByDate() {
        List<Restaurant> restaurantsFromDatabase = service.getAllByDate(LocalDate.of(2021, 9, 1));
        MATCHER.assertMatch(restaurantsFromDatabase, List.of(burgerKing));
    }

    @Test
    void createWithException() {
        assertThrows(ConstraintViolationException.class, () -> service.create(new Restaurant(null, "  ", null, null)));
    }
}