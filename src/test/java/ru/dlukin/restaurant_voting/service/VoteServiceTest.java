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

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.dlukin.restaurant_voting.testdata.RestaurantTestData.kfc;
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
        Vote created = service.create(kfc.getId(), UserTestData.newUserForVote);
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        MATCHER.assertMatch(created, newVote);
    }

    @Test
    void getAll() {
        List<Vote> all = service.getAll();
        MATCHER.assertMatch(all, vote1, vote2, vote3);
    }

    @Test
    void delete() {
        service.delete(VOTE_ID);
        assertThrows(NotFoundException.class, () -> service.delete(VOTE_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_ID));
    }

    @Test
    void getAllByRestaurant() {
        List<Vote> all = service.getAllByRestaurant(mcDonalds.getId());
        MATCHER.assertMatch(all, vote1, vote2);
    }

    @Test
    void getAllByRestaurantAndDateVote() {
        List<Vote> all = service.getAllByRestaurantAndDateVote(mcDonalds.getId(), LocalDate.now());
        MATCHER.assertMatch(all, vote1, vote2);
    }

    @Test
    void getRatingByDate() {
        Map<String, Integer> getRatingByDate = service.getRatingByDate(LocalDate.now());
      //  MATCHER.assertMatch(getRatingByDate, voteMapResult);  //TODO: Как правильно написать данный MATCHER?
    }

    @Test
    void createWithException() {
        assertThrows(ConstraintViolationException.class, () -> service.create(mcDonalds.getId(), null));
        assertThrows(DataIntegrityViolationException.class, () -> service.create(0, UserTestData.newUserForVote));
    }
}