package ru.dlukin.restaurant_voting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.dlukin.restaurant_voting.model.MenuItem;
import ru.dlukin.restaurant_voting.repository.MenuItemRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dlukin.restaurant_voting.testdata.MenuItemTestData.*;
import static ru.dlukin.restaurant_voting.testdata.RestaurantTestData.kfc;
import static ru.dlukin.restaurant_voting.util.JsonUtil.writeValue;

class MenuItemControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/api/admin/restaurants/1/menu-items/";

    @Autowired
    private MenuItemRepository repository;

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void createWithLocation() throws Exception {
        MenuItem newMenuItem = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMenuItem)))
                .andExpect(status().isCreated());

        MenuItem created = MENU_ITEM_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenuItem.setId(newId);
        MENU_ITEM_MATCHER.assertMatch(created, newMenuItem);
        MENU_ITEM_MATCHER.assertMatch(repository.getById(newId), newMenuItem);
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void createInvalid() throws Exception {
        MenuItem invalid = new MenuItem(null, null, null, 0, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void createDuplicateName() throws Exception {
        MenuItem duplicate = new MenuItem(null, "Chicken Basket", LocalDate.now(), 250, kfc);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(duplicate)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void update() throws Exception {
        MenuItem updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + MENU_ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MENU_ITEM_MATCHER.assertMatch(repository.getById(MENU_ITEM_ID), getUpdated());
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void updateInvalid() throws Exception {
        MenuItem invalid = new MenuItem(MENU_ITEM_ID, "", null, 10, null);
        perform(MockMvcRequestBuilders.put(REST_URL + MENU_ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MENU_ITEM_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(MENU_ITEM_ID).isPresent());
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
        perform(MockMvcRequestBuilders.get(REST_URL + MENU_ITEM_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(MENU_ITEM_1));
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void getAllByRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(MENU_ITEM_1, MENU_ITEM_2, MENU_ITEM_3));
    }

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void getAllByRestaurantAndDate() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/admin/restaurants/3/menu-items/?date=2021-09-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(List.of(MENU_ITEM_TEST_DATE)));
    }
}