package ru.dlukin.restaurant_voting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.dlukin.restaurant_voting.service.DishService;
import ru.dlukin.restaurant_voting.web.admin.AdminDishController;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminDishController.REST_URL + '/';

    @Test
    @WithMockUser(username = "admin@restaurant.com", password = "admin", roles = "ADMIN")
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "{restaurantId}/dishes/{dish}", "1", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Chicken Combo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("150"));
    }
}