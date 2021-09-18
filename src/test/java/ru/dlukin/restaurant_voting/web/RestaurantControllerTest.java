package ru.dlukin.restaurant_voting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.repository.RestaurantRepository;
import ru.dlukin.restaurant_voting.web.admin.AdminRestaurantController;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dlukin.restaurant_voting.testdata.RestaurantTestData.*;
import static ru.dlukin.restaurant_voting.util.JsonUtil.writeValue;

class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantController.REST_URL + '/';

    @Autowired
    private RestaurantRepository repository;

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(repository.getById(newId), newRestaurant);
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void createInvalid() throws Exception {
        Restaurant invalid = new Restaurant(null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void createDuplicateName() throws Exception {
        Restaurant duplicate = new Restaurant(null, "KFC", null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(duplicate)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void update() throws Exception {
        Restaurant updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + KFC_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(repository.getById(KFC_ID), getUpdated());
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void updateInvalid() throws Exception {
        Restaurant invalid = new Restaurant(KFC_ID, "", null);
        perform(MockMvcRequestBuilders.put(REST_URL + KFC_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + KFC_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(KFC_ID).isPresent());
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + KFC_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(kfc));
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(kfc, mcDonalds, burgerKing));
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void getByName() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by-name?name=KFC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(kfc));
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by-date?date=2021-09-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(List.of(burgerKing)));
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void getByToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by-today"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(kfc, mcDonalds));
    }
}