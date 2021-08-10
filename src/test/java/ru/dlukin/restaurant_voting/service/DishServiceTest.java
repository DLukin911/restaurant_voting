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
import static ru.dlukin.restaurant_voting.testdata.RestaurantTestData.mcDonalds;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DishServiceTest {

    @Autowired
    private DishService service;

    @Test
    void create() {
        Dish created = service.create(getNew());
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        MATCHER.assertMatch(created, newDish);
        MATCHER.assertMatch(service.get(newId), newDish);
    }

    @Test
    void get() {
        Dish dishFromDatabase = service.get(DISH_ID);
        MATCHER.assertMatch(dish1, dishFromDatabase);
    }

    @Test
    void getNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.get(NOT_FOUND_ID));
    }

    @Test
    void getAll() {
        List<Dish> all = service.getAll();
        MATCHER.assertMatch(all, dish1, dish2, dish3, dish4, dish5);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.update(updated);
        MATCHER.assertMatch(service.get(DISH_ID), getUpdated());
    }

    @Test
    void delete() {
        service.delete(DISH_ID);
        assertThrows(EntityNotFoundException.class, () -> service.get(DISH_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID));
    }

    @Test
    void findAllByRestaurantAndDateMenu() {
        List<Dish> all = service.findAllByRestaurantAndDateMenu(kfc, LocalDate.now());
        MATCHER.assertMatch(all, dish1, dish2, dish3);
    }

    @Test
    void findAllByRestaurant() {
        List<Dish> all = service.findAllByRestaurant(mcDonalds);
        MATCHER.assertMatch(all, dish4, dish5);
    }

    @Test
    void createWithException() {
        assertThrows(ConstraintViolationException.class, () -> service.create(new Dish(null, "  ", LocalDate.now(),
                150, kfc)));
        assertThrows(ConstraintViolationException.class, () -> service.create(new Dish(null, "Egg", LocalDate.now(),
                0, kfc)));
        assertThrows(ConstraintViolationException.class, () -> service.create(new Dish(null, "Egg", LocalDate.now(),
                150, null)));
        assertThrows(DataIntegrityViolationException.class, () -> service.create(new Dish(null, "Chicken Basket",
                LocalDate.now(), 250, kfc)));
    }
}