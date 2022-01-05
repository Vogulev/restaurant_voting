package ru.vogulev.voting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.vogulev.voting.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    List<Dish> findAllByRestaurantIdOrderByAddDate(int restaurantId);

    List<Dish> findAllByRestaurantIdAndAddDate(int restaurantId, LocalDate addDate);
}
