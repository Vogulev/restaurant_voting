package ru.vogulev.voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vogulev.voting.model.Restaurant;
import ru.vogulev.voting.model.User;
import ru.vogulev.voting.model.Vote;
import ru.vogulev.voting.repository.RestaurantRepository;
import ru.vogulev.voting.repository.VoteRepository;
import ru.vogulev.voting.util.exception.IllegalRequestDataException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ru.vogulev.voting.util.validation.ValidationUtil.checkNotFound;
import static ru.vogulev.voting.web.SecurityUtil.authId;
import static ru.vogulev.voting.web.SecurityUtil.authUser;

@Slf4j
@Service
@AllArgsConstructor
public class VoteService {

    private static final LocalTime VOTING_DEADLINE = LocalTime.of(11,0);

    private VoteRepository voteRepository;
    private RestaurantRepository restaurantRepository;

    public Vote create(int restaurantId) {
        User currentUser = authUser();
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        log.info("user with id{} voted for restaurant with id{}", currentUser.id(), restaurantId);
        return voteRepository.save(new Vote(currentUser, restaurant));
    }

    public void delete(int id) {
        log.info("delete {}", id);
        voteRepository.deleteExisted(id);
    }

    public Optional<Vote> get(int id) {
        log.info("get {}", id);
        return voteRepository.findById(id);
    }

    public List<Vote> getAllByRestaurantId(int restaurantId) {
        log.info("getAll by restaurant with id{}", restaurantId);
        return voteRepository.findAllByRestaurantIdOrderByVoteDate(restaurantId);
    }

    public List<Vote> getAllByUserId(int userId) {
        log.info("getAll by user with id{}", userId);
        return voteRepository.findAllByUserIdOrderByVoteDate(userId);
    }

    public void update(int restaurantId) {
        log.info("update vote for restaurant with id={}", restaurantId);
        Vote updated = checkNotFound(voteRepository.findVoteByUserIdAndVoteDate(authId(), LocalDate.now()),
                "id:" + restaurantId);
        if (LocalTime.now().isBefore(VOTING_DEADLINE)) {
            updated.setRestaurant(restaurantRepository.getById(restaurantId));
            voteRepository.save(updated);
        } else {
            throw new IllegalRequestDataException("It's too late, vote can't be changed");
        }
    }
}
