package ru.vogulev.voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.vogulev.voting.model.Vote;
import ru.vogulev.voting.repository.VoteRepository;
import ru.vogulev.voting.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.vogulev.voting.web.restaurant.RestaurantTestData.*;
import static ru.vogulev.voting.web.user.UserTestData.*;
import static ru.vogulev.voting.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_YESTERDAY_VOTE_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(user_yesterday_vote));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_YESTERDAY_VOTE_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotAuthUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_YESTERDAY_VOTE_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(List.of(user_yesterday_vote)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            perform(MockMvcRequestBuilders.delete(REST_URL + USER_YESTERDAY_VOTE_ID))
                    .andExpect(status().isNoContent());
            VOTE_MATCHER.assertMatch(voteRepository.findAllByUserIdOrderByVoteDateDesc(USER_ID), List.of());
        } else {
            perform(MockMvcRequestBuilders.delete(REST_URL + USER_YESTERDAY_VOTE_ID)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
        }
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            perform(MockMvcRequestBuilders.put(REST_URL + BURGER_SHOP_ID)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNoContent());

            VOTE_MATCHER.assertMatch(
                    voteRepository.findVoteByUserIdAndVoteDate(ADMIN_ID, LocalDate.now()), admin_today_vote_updated);
        } else {
            perform(MockMvcRequestBuilders.put(REST_URL + BURGER_SHOP_ID)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithLocation() throws Exception {
        Vote newVote = new Vote(user, premium_restaurant);

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + PREMIUM_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteRepository.getById(newId), newVote);
    }
}