package ru.dlukin.restaurant_voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import ru.dlukin.restaurant_voting.model.Dish;
import ru.dlukin.restaurant_voting.util.exception.NotFoundException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.dlukin.restaurant_voting.testdata.DishTestData.*;
import static ru.dlukin.restaurant_voting.testdata.RestaurantTestData.kfc;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DishServiceTest {

    @Autowired
    private DishService service;

    @Test
    void create() {
        Dish created = service.create(getNew(), 1);
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        MATCHER.assertMatch(created, newDish);
        MATCHER.assertMatch(service.get(newId), newDish);
    }

    @Test
    void get() {
        Dish dishFromDatabase = service.get(DISH_ID);
        MATCHER.assertMatch(dishFromDatabase, dish1);
    }

    @Test
    void getNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.get(NOT_FOUND_ID));
    }

    @Test
    void getAll() {
        List<Dish> all = service.getAllByRestaurantId(1);
        MATCHER.assertMatch(all, dish1, dish2, dish3);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.update(updated, 2);
        MATCHER.assertMatch(service.get(DISH_ID), getUpdated());
    }

    @Test
    void delete() {
        service.delete(DISH_ID, 1);
        assertThrows(EntityNotFoundException.class, () -> service.get(DISH_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID, 1));
    }

    @Test
    void getAllByRestaurantIdAndDateVote() {
        List<Dish> all = service.getAllByRestaurantIdAndDateVote(1, LocalDate.now());
        MATCHER.assertMatch(all, dish1, dish2, dish3);
    }

    @Test
    void getByIdAndRestaurantId() {
        Dish all = service.getByIdAndRestaurantId(4, 2);
        MATCHER.assertMatch(all, dish4);
    }

    @Test
    void createWithException() {
        assertThrows(ConstraintViolationException.class, () -> service.create(new Dish(null, "  ", LocalDate.now(),
                150, kfc), 1));
        assertThrows(ConstraintViolationException.class, () -> service.create(new Dish(null, "Egg", LocalDate.now(),
                0, kfc), 1));
        assertThrows(DataIntegrityViolationException.class, () -> service.create(new Dish(null, "Chicken Basket",
                LocalDate.now(), 250, kfc), 1));
    }
}