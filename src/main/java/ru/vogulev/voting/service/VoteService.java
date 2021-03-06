package ru.vogulev.voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vogulev.voting.model.Restaurant;
import ru.vogulev.voting.model.Vote;
import ru.vogulev.voting.repository.RestaurantRepository;
import ru.vogulev.voting.repository.VoteRepository;
import ru.vogulev.voting.to.VoteTo;
import ru.vogulev.voting.util.exception.IllegalRequestDataException;
import ru.vogulev.voting.util.exception.NotFoundException;
import ru.vogulev.voting.web.AuthUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ru.vogulev.voting.util.validation.ValidationUtil.checkNotFound;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class VoteService {

    private static final LocalTime VOTING_DEADLINE = LocalTime.of(11,0);

    private VoteRepository voteRepository;
    private RestaurantRepository restaurantRepository;

    @Transactional
    public Vote create(int restaurantId, AuthUser authUser) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->
                new NotFoundException("no restaurant with id " + restaurantId + " were found!"));
        log.info("user with id{} voted for restaurant with id{}", authUser.id(), restaurantId);
        return voteRepository.save(new Vote(authUser.getUser(), restaurant));
    }

    @Transactional
    public void delete(int id, int userId) {
        if (checkVotingDeadline()) {
            log.info("delete {}", id);
            voteRepository.deleteVoteByIdAndUserId(id, userId);
        } else {
            throw new IllegalRequestDataException("It's too late, vote can't be deleted");
        }
    }

    public Optional<Vote> get(int id, int userId) {
        log.info("get {}", id);
        return voteRepository.findByIdAndUserId(id, userId);
    }

    public List<VoteTo> getAllByRestaurantId(int restaurantId) {
        log.info("getAll by restaurant with id{}", restaurantId);
        return voteRepository.findAllByRestaurantIdOrderByVoteDate(restaurantId);
    }

    public List<Vote> getAllByUserId(int userId) {
        log.info("getAll by user with id{}", userId);
        return voteRepository.findAllByUserIdOrderByVoteDateDesc(userId);
    }

    @Transactional
    public void update(int restaurantId, int userid) {
        Vote updated = checkNotFound(voteRepository.findVoteByUserIdAndVoteDate(userid, LocalDate.now()),
                "id:" + restaurantId);
        if (checkVotingDeadline()) {
            log.info("update vote for restaurant with id={}", restaurantId);
            updated.setRestaurant(restaurantRepository.getById(restaurantId));
            voteRepository.save(updated);
        } else {
            throw new IllegalRequestDataException("It's too late, vote can't be changed");
        }
    }

    public List<VoteTo> getAllByDate(LocalDate voteDate) {
        log.info("getAll by date{}", voteDate);
        return voteRepository.findAllByVoteDate(voteDate);
    }

    private boolean checkVotingDeadline() {
        return LocalTime.now().isBefore(VOTING_DEADLINE);
    }
}
