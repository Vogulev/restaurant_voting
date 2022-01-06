package ru.vogulev.voting.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.vogulev.voting.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    List<Dish> findAllByRestaurantIdOrderByAddDate(int restaurantId);

    List<Dish> findAllByRestaurantIdAndAddDate(int restaurantId, LocalDate addDate);

    Optional<Dish> findByIdAndRestaurantId(int id, int restaurantId);

    @Transactional
    @Modifying
    int deleteByIdAndRestaurantId(int id, int restaurantId);
}
