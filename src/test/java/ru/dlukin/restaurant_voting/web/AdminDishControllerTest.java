package ru.dlukin.restaurant_voting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.dlukin.restaurant_voting.service.DishService;
import ru.dlukin.restaurant_voting.web.admin.AdminDishController;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dlukin.restaurant_voting.TestUtil.userHttpBasic;
import static ru.dlukin.restaurant_voting.testdata.DishTestData.*;
import static ru.dlukin.restaurant_voting.testdata.UserTestData.admin;

@Transactional
@WebMvcTest(AdminDishController.class)
public class AdminDishControllerTest {
 /*   public MockMvc mockMvc;  //TODO: Как правильно мокнуть, чтобы тесты заработали?

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    private static final String REST_URL = AdminDishController.REST_URL + '/';

    @Autowired
    private DishService service;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/restaurants/1/dishes/1")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(dish1));
    }*/

}
