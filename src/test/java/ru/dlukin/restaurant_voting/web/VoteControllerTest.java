package ru.dlukin.restaurant_voting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.dlukin.restaurant_voting.model.Vote;
import ru.dlukin.restaurant_voting.repository.VoteRepository;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dlukin.restaurant_voting.testdata.RestaurantTestData.kfc;
import static ru.dlukin.restaurant_voting.testdata.UserTestData.USER_MAIL;
import static ru.dlukin.restaurant_voting.testdata.UserTestData.user;
import static ru.dlukin.restaurant_voting.testdata.VoteTestData.*;
import static ru.dlukin.restaurant_voting.util.JsonUtil.writeValue;
import static ru.dlukin.restaurant_voting.web.user.UserVoteController.REST_URL;

class VoteControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteRepository repository;

    @Test
    @WithUserDetails(value = "userfortest@gmail.com")
    void createWithLocation() throws Exception {
        Vote newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("restaurantId", "1")
                .content(writeValue(newVote)))
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(repository.getById(newId), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createDuplicate() throws Exception {
        Vote duplicate = new Vote(null, kfc, user);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("restaurantId", "1")
                .content(writeValue(duplicate)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        Vote updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("time", "10:00")
                .param("restaurantId", "1")
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        VOTE_MATCHER.assertMatch(repository.getById(VOTE_ID), getUpdated());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateInvalidAfterDeadLineTime() throws Exception {
        Vote updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("time", "12:00")
                .param("restaurantId", "1")
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = "userfortest@gmail.com")
    void updateEmptyVote() throws Exception {
        Vote updated = new Vote(voteNew);
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("time", "10:00")
                .param("restaurantId", "1")
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(List.of(vote1)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByUserForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/today-vote"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(vote1));
    }
}