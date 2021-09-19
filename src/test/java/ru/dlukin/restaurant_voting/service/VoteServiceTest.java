package ru.dlukin.restaurant_voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.Vote;
import ru.dlukin.restaurant_voting.testdata.UserTestData;
import ru.dlukin.restaurant_voting.util.exception.IllegalRequestDataException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.dlukin.restaurant_voting.testdata.RestaurantTestData.kfc;
import static ru.dlukin.restaurant_voting.testdata.RestaurantTestData.mcDonalds;
import static ru.dlukin.restaurant_voting.testdata.UserTestData.user;
import static ru.dlukin.restaurant_voting.testdata.VoteTestData.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class VoteServiceTest {

    @Autowired
    private VoteService service;

    @Test
    @Transactional
    void create() {
        Vote created = service.create(kfc, UserTestData.newUserForVote);
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.getVoteByDateVoteAndUser(LocalDate.now(), UserTestData.newUserForVote), voteNew);
    }

    @Test
    @Transactional
    void createDuplicate() {
        service.create(kfc, UserTestData.newUserForVote);
        assertThrows(IllegalRequestDataException.class, () -> service.create(kfc, UserTestData.newUserForVote));
    }

  /*  @Test
    @Transactional
    void update() {
        Vote updated = getUpdated();
        service.update(user);
        DISH_MATCHER.assertMatch(service.get(DISH_ID), getUpdated());
    }*/

    @Test
    @Transactional
    void getAllByUser() {
        List<Vote> all = service.getAllByUser(user);
        VOTE_MATCHER.assertMatch(all, vote1);
    }

  /*  @Test
    @Transactional
    void getVoteByDateVoteAndUser() {
        Vote vote = service.getVoteByDateVoteAndUser(LocalDate.now(), user);
        VOTE_MATCHER.assertMatch(vote, vote1);
    }*/

    @Test
    void createWithException() {
        assertThrows(ConstraintViolationException.class, () -> service.create(mcDonalds, null));
        assertThrows(ConstraintViolationException.class, () -> service.create(null, UserTestData.newUserForVote));
    }
}