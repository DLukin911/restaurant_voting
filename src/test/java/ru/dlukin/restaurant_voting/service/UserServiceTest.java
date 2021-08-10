package ru.dlukin.restaurant_voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import ru.dlukin.restaurant_voting.model.Role;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.util.exception.NotFoundException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.dlukin.restaurant_voting.testdata.UserTestData.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    void create() {
        User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        MATCHER.assertMatch(created, newUser);
        MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () -> service.create(new User(null, "Duplicate", "user1@gmail.com",
                "newPass", Role.USER)));
    }

    @Test
    void get() {
        User userFromDatabase = service.get(USER_ID);
        MATCHER.assertMatch(user, userFromDatabase);
    }

    @Test
    void getNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.get(NOT_FOUND_ID));
    }

    @Test
    void getAll() {
        List<User> all = service.getAll();
        MATCHER.assertMatch(all, user, user2, user3, admin);
    }

    @Test
    void update() {
        User updated = getUpdated();
        service.update(updated);
        MATCHER.assertMatch(service.get(USER_ID), getUpdated());
    }

    @Test
    void delete() {
        service.delete(USER_ID);
        assertThrows(EntityNotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID));
    }

    @Test
    void findByEmail() {
        User userFromDatabase = service.findByEmail("user1@gmail.com");
        MATCHER.assertMatch(user, userFromDatabase);
    }

    @Test
    void findByName() {
        User userFromDatabase = service.findByName("User1_Name");
        MATCHER.assertMatch(user, userFromDatabase);
    }

    @Test
    void createWithException() {
        assertThrows(ConstraintViolationException.class, () -> service.create(new User(null, "  ", "mail@yandex.ru",
                "password", Role.USER)));
        assertThrows(ConstraintViolationException.class, () -> service.create(new User(null, "  ", "mail@yandex.ru",
                "password", Role.USER)));
        assertThrows(ConstraintViolationException.class, () -> service.create(new User(null, "User", "  ", "password",
                Role.USER)));
        assertThrows(ConstraintViolationException.class, () -> service.create(new User(null, "User", "mail@yandex.ru",
                "  ", Role.USER)));
        assertThrows(DataIntegrityViolationException.class, () -> service.create(new User(null, "User1_Name", "user1@gmail.com",
                "password", Role.USER)));
    }
}