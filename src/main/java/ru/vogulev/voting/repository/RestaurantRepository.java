package ru.vogulev.voting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.vogulev.voting.model.Restaurant;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
}
