package ru.vogulev.voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.vogulev.voting.model.Restaurant;
import ru.vogulev.voting.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("""
            SELECT new ru.vogulev.voting.to.RestaurantTo(r.id, r.name, count(r.id))
            FROM Restaurant r LEFT JOIN Vote v ON r.id=v.restaurant.id
            WHERE v.voteDate=:voteDate
            GROUP BY r.id
            ORDER BY count(v.restaurant) DESC
            """)
    List<RestaurantTo> findAllRestaurantsWithVotesCountOnDate(LocalDate voteDate);

    @Query("""
            SELECT new ru.vogulev.voting.to.RestaurantTo(r.id, r.name, count(r.id))
            FROM Restaurant r LEFT JOIN Vote v ON r.id=v.restaurant.id
            WHERE r.id=?1
            """)
    Optional<RestaurantTo> findRestaurantWithVotesById(int id);
}
