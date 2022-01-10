package ru.vogulev.voting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.vogulev.voting.model.Restaurant;
import ru.vogulev.voting.repository.RestaurantRepository;
import ru.vogulev.voting.util.JsonUtil;
import ru.vogulev.voting.util.exception.NotFoundException;
import ru.vogulev.voting.web.AbstractControllerTest;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.vogulev.voting.web.restaurant.RestaurantTestData.*;
import static ru.vogulev.voting.web.user.UserTestData.ADMIN_MAIL;
import static ru.vogulev.voting.web.user.UserTestData.USER_MAIL;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    private static final String REST_URL = AdminRestaurantController.REST_URL + '/';

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + premium_restaurant.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(premium_restaurant));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + premium_restaurant.id()))
                .andExpect(status().isForbidden());
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + premium_restaurant.id()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + premium_restaurant.id()))
                .andExpect(status().isNoContent());
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.findAll(Sort.by("name")), restaurants_after_delete);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + premium_restaurant.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new Restaurant(null, "Updated Premium restaurant"))))
                .andDo(print())
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(restaurantRepository.findById(premium_restaurant.id())
                        .orElseThrow(() ->
                                new NotFoundException("no restaurant with id " + premium_restaurant.id() + " were found!")),
                premium_restaurant_updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "newPremiumRestaurantDish");

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getById(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllWithVotesCountOnToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "count")
                .param("voteDate", String.valueOf(LocalDate.now())))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(restaurants_with_votes_on_today));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllWithVotesCountOnDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "count")
                .param("voteDate", "1988-12-22"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson());
    }
}