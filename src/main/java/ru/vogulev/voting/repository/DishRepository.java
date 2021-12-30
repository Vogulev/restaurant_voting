package ru.vogulev.voting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.vogulev.voting.model.Dish;

import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    List<Dish> findAllByRestaurantIdOrderByAddDate(int restaurantId);
}
