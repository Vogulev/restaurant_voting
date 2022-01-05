package ru.vogulev.voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.vogulev.voting.model.Vote;
import ru.vogulev.voting.to.VoteTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    Optional<Vote> findByIdAndUserId(int id, int userId);

    List<Vote> findAllByRestaurantIdOrderByVoteDate(int restaurantId);

    List<Vote> findAllByUserIdOrderByVoteDate(int userId);

    Vote findVoteByUserIdAndVoteDate(int userId, LocalDate voteDate);

    void deleteVoteByIdAndUserId(int id, int userId);

    @Query("""
            SELECT new ru.vogulev.voting.to.VoteTo(v.id, v.voteDate, r.id, r.name, v.user.id, v.user.name)
            FROM Vote v LEFT OUTER JOIN Restaurant r ON v.restaurant.id=r.id
            WHERE v.voteDate=:voteDate
            GROUP BY v.id
            ORDER BY v.voteDate DESC
            """)
    List<VoteTo> findAllByVoteDate(LocalDate voteDate);
}
