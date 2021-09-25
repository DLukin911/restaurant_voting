package ru.dlukin.restaurant_voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.MenuItem;
import ru.dlukin.restaurant_voting.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.dlukin.restaurant_voting.testdata.MenuItemTestData.*;
import static ru.dlukin.restaurant_voting.testdata.RestaurantTestData.kfc;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MenuItemServiceTest {

    @Autowired
    private MenuItemService service;

    @Test
    void create() {
        MenuItem created = service.create(getNew(), 1);
        int newId = created.id();
        MenuItem newMenuItem = getNew();
        newMenuItem.setId(newId);
        MENU_ITEM_MATCHER.assertMatch(created, newMenuItem);
        MENU_ITEM_MATCHER.assertMatch(service.get(newId), newMenuItem);
    }

    @Test
    @Transactional
    void update() {
        MenuItem updated = getUpdated();
        service.update(updated, 2);
        MENU_ITEM_MATCHER.assertMatch(service.get(MENU_ITEM_ID), getUpdated());
    }

    @Test
    void delete() {
        service.delete(MENU_ITEM_ID, 1);
        assertThrows(NotFoundException.class, () -> service.get(MENU_ITEM_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID, 1));
    }

    @Test
    void get() {
        MenuItem menuItemFromDb = service.get(MENU_ITEM_1.getId());
        MENU_ITEM_MATCHER.assertMatch(menuItemFromDb, MENU_ITEM_1);
    }

    @Test
    void getAllByRestaurant() {
        List<MenuItem> all = service.getAllByRestaurantId(1);
        MENU_ITEM_MATCHER.assertMatch(all, MENU_ITEM_1, MENU_ITEM_2, MENU_ITEM_3);
    }

    @Test
    void getAllByRestaurantAndDate() {
        List<MenuItem> all = service.getAllByRestaurantIdAndDate(1, LocalDate.now());
        MENU_ITEM_MATCHER.assertMatch(all, MENU_ITEM_1, MENU_ITEM_2, MENU_ITEM_3);
    }

    @Test
    void getByIdAndRestaurant() {
        MenuItem all = service.getByIdAndRestaurantId(4, 2);
        MENU_ITEM_MATCHER.assertMatch(all, MENU_ITEM_4);
    }

    @Test
    void createWithException() {
        assertThrows(ConstraintViolationException.class, () -> service.create(new MenuItem(null, "  ", LocalDate.now(),
                150, kfc), 1));
        assertThrows(DataIntegrityViolationException.class, () -> service.create(new MenuItem(null, "Chicken Basket",
                LocalDate.now(), 250, kfc), 1));
    }
}