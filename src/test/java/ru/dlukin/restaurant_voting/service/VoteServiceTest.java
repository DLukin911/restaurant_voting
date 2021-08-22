package ru.dlukin.restaurant_voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.Vote;
import ru.dlukin.restaurant_voting.testdata.UserTestData;
import ru.dlukin.restaurant_voting.util.exception.NotFoundException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.dlukin.restaurant_voting.testdata.RestaurantTestData.mcDonalds;
import static ru.dlukin.restaurant_voting.testdata.VoteTestData.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class VoteServiceTest {

    @Autowired
    private VoteService service;

    @Test
    @Transactional
    void create() {
        Vote created = service.create(getNew());
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        MATCHER.assertMatch(created, newVote);
        MATCHER.assertMatch(service.get(newId), newVote);
    }

    @Test
    @Transactional
    void get() {
        Vote voteFromDatabase = service.get(VOTE_ID);
        MATCHER.assertMatch(vote1, voteFromDatabase);
    }

    @Test
    void getNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.get(NOT_FOUND_ID));
    }

    @Test
    void getAll() {
        List<Vote> all = service.getAll();
        MATCHER.assertMatch(all, vote1, vote2, vote3);
    }

    @Test
    void update() {
        Vote updated = getUpdated();
        service.update(updated);
        MATCHER.assertMatch(service.get(VOTE_ID), getUpdated());
    }

    @Test
    void delete() {
        service.delete(VOTE_ID);
        assertThrows(EntityNotFoundException.class, () -> service.get(VOTE_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID));
    }

    @Test
    void findAllByDateVote() {
        List<Vote> all = service.findAllByDateVote(LocalDate.now());
        MATCHER.assertMatch(all, vote1, vote2, vote3);
    }

    @Test
    void findAllByRestaurant() {
        List<Vote> all = service.findAllByRestaurant(mcDonalds);
        MATCHER.assertMatch(all, vote1, vote2);
    }

    @Test
    void findAllByUser() {
        List<Vote> all = service.findAllByUser(UserTestData.user);
        MATCHER.assertMatch(all, vote1);
    }

    @Test
    void findAllByRestaurantAndDateVote() {
        List<Vote> all = service.findAllByRestaurantAndDateVote(mcDonalds, LocalDate.now());
        MATCHER.assertMatch(all, vote1, vote2);
    }

    @Test
    void createWithException() {
        assertThrows(ConstraintViolationException.class, () -> service.create(new Vote(null, mcDonalds, null)));
        assertThrows(ConstraintViolationException.class, () -> service.create(new Vote(null, null, UserTestData.user)));
        service.create(new Vote(null, LocalDateTime.of(2021, 10, 11, 12, 15), mcDonalds, UserTestData.user));
        assertThrows(DataIntegrityViolationException.class, () ->
                service.create(new Vote(null, LocalDateTime.of(2021, 10, 11, 12, 15), mcDonalds, UserTestData.user)));
    }
}