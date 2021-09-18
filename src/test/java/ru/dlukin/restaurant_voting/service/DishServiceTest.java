package ru.dlukin.restaurant_voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import ru.dlukin.restaurant_voting.model.Dish;
import ru.dlukin.restaurant_voting.util.exception.NotFoundException;

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
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId), newDish);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.update(updated, 2);
        DISH_MATCHER.assertMatch(service.get(DISH_ID), getUpdated());
    }

    @Test
    void delete() {
        service.delete(DISH_ID, 1);
        assertThrows(NotFoundException.class, () -> service.get(DISH_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID, 1));
    }

    @Test
    void get() {
        Dish dishFromDb = service.get(dish1.getId());
        DISH_MATCHER.assertMatch(dishFromDb, dish1);
    }

    @Test
    void getAllByRestaurant() {
        List<Dish> all = service.getAllByRestaurantId(1);
        DISH_MATCHER.assertMatch(all, dish1, dish2, dish3);
    }

    @Test
    void getAllByRestaurantAndDate() {
        List<Dish> all = service.getAllByRestaurantIdAndDate(1, LocalDate.now());
        DISH_MATCHER.assertMatch(all, dish1, dish2, dish3);
    }

    @Test
    void getByIdAndRestaurant() {
        Dish all = service.getByIdAndRestaurantId(4, 2);
        DISH_MATCHER.assertMatch(all, dish4);
    }

    @Test
    void createWithException() {
        assertThrows(ConstraintViolationException.class, () -> service.create(new Dish(null, "  ", LocalDate.now(),
                150, kfc), 1));
        assertThrows(DataIntegrityViolationException.class, () -> service.create(new Dish(null, "Chicken Basket",
                LocalDate.now(), 250, kfc), 1));
    }
}