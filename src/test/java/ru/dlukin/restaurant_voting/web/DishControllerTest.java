package ru.dlukin.restaurant_voting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.dlukin.restaurant_voting.model.Dish;
import ru.dlukin.restaurant_voting.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dlukin.restaurant_voting.testdata.DishTestData.*;
import static ru.dlukin.restaurant_voting.testdata.RestaurantTestData.kfc;
import static ru.dlukin.restaurant_voting.util.JsonUtil.writeValue;

class DishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/api/admin/restaurants/1/dishes/";

    @Autowired
    private DishRepository repository;

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void createWithLocation() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newDish)))
                .andExpect(status().isCreated());

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(repository.getById(newId), newDish);
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, null, null, 0, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void createDuplicateName() throws Exception {
        Dish duplicate = new Dish(null, "Chicken Basket", LocalDate.now(), 250, kfc);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(duplicate)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void update() throws Exception {
        Dish updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(repository.getById(DISH_ID), getUpdated());
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void updateInvalid() throws Exception {
        Dish invalid = new Dish(DISH_ID, "", null, 10, null);
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(DISH_ID).isPresent());
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
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1));
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void getAllByRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1, dish2, dish3));
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void getAllByRestaurantAndDate() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/admin/restaurants/3/dishes/?date=2021-09-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(List.of(dishTestDate)));
    }
}