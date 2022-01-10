package ru.vogulev.voting.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.vogulev.voting.model.Dish;
import ru.vogulev.voting.repository.DishRepository;
import ru.vogulev.voting.util.JsonUtil;
import ru.vogulev.voting.util.exception.NotFoundException;
import ru.vogulev.voting.web.AbstractControllerTest;
import ru.vogulev.voting.web.restaurant.AdminRestaurantController;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.vogulev.voting.web.dish.DishTestData.*;
import static ru.vogulev.voting.web.restaurant.RestaurantTestData.premium_restaurant;
import static ru.vogulev.voting.web.user.UserTestData.*;

class AdminDishControllerTest extends AbstractControllerTest {

    @Autowired
    private DishRepository dishRepository;

    private static final String REST_URL = AdminRestaurantController.REST_URL + '/';

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + premium_restaurant.id() + "/dishes/" + premium_restaurant_dish1.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(Dish_MATCHER.contentJson(premium_restaurant_dish1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotByAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + premium_restaurant.id() + "/dishes/" + premium_restaurant_dish1.id()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + premium_restaurant.id() + "/dishes/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(Dish_MATCHER.contentJson(premium_restaurant_history_menu));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + premium_restaurant.id() + "/dishes/" + premium_restaurant_dish1.id()))
                .andExpect(status().isNoContent());
        Dish_MATCHER.assertMatch(dishRepository.findAllByRestaurantIdOrderByAddDate(premium_restaurant.id()),
                premium_restaurant_yesterdayDish, premium_restaurant_dish2, premium_restaurant_dish3, premium_restaurant_dish4, premium_restaurant_dish5);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + premium_restaurant.id() + "/dishes/" + NOT_FOUND))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + premium_restaurant.id() + "/dishes/" + premium_restaurant_dish1.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new Dish("newPremiumRestaurantDish", 99.9))))
                .andDo(print())
                .andExpect(status().isNoContent());

        Dish_MATCHER.assertMatch(dishRepository.findByIdAndRestaurantId(premium_restaurant_dish1.id(), premium_restaurant.id())
                        .orElseThrow(() ->
                                new NotFoundException("no dish with id " + premium_restaurant_dish1.id() + " were found!")),
                premium_restaurant_updatedDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + premium_restaurant.id() + "/dishes/" + premium_restaurant_dish1.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new Dish("D", 0))))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Dish newDish = new Dish("newPremiumRestaurantDish", 99.9);

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + premium_restaurant.id() + "/dishes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish created = Dish_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        Dish_MATCHER.assertMatch(created, newDish);
        Dish_MATCHER.assertMatch(dishRepository.getById(newId), newDish);
    }
}