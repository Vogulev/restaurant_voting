package ru.vogulev.voting.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.vogulev.voting.web.AbstractControllerTest;
import ru.vogulev.voting.web.restaurant.RestaurantController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.vogulev.voting.web.dish.DishTestData.Dish_MATCHER;
import static ru.vogulev.voting.web.dish.DishTestData.premium_restaurant_menu;
import static ru.vogulev.voting.web.restaurant.RestaurantTestData.PREMIUM_RESTAURANT_ID;
import static ru.vogulev.voting.web.user.UserTestData.USER_MAIL;

class DishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + '/';

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getMenuForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + PREMIUM_RESTAURANT_ID + "/dishes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(Dish_MATCHER.contentJson(premium_restaurant_menu));
    }
}