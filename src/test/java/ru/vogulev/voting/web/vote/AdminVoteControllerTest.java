package ru.vogulev.voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.vogulev.voting.repository.VoteRepository;
import ru.vogulev.voting.web.AbstractControllerTest;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.vogulev.voting.web.restaurant.RestaurantTestData.*;
import static ru.vogulev.voting.web.user.UserTestData.*;
import static ru.vogulev.voting.web.vote.VoteTestData.*;

class AdminVoteControllerTest extends AbstractControllerTest {

    @Autowired
    VoteRepository voteRepository;

    private static final String REST_URL = AdminVoteController.REST_URL + '/';

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllByRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "restaurant/" + BURGER_SHOP_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(burger_shop_votes));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllOnToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "today"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(today_votes));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllOnTodayNotByAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "today"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllOnDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL).param("voteDate", String.valueOf(LocalDate.now().minusDays(1))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(yesterday_votes));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllByUserId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "user/" + ADMIN_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(admin_votes));
    }
}