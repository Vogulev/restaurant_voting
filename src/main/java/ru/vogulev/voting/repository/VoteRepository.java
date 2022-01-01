package ru.vogulev.voting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.vogulev.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    List<Vote> findAllByRestaurantIdOrderByVoteDate(int restaurantId);

    List<Vote> findAllByUserIdOrderByVoteDate(int userId);

    Vote findVoteByUserIdAndVoteDate(int userId, LocalDate voteDate);
}
