package ru.vogulev.voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.vogulev.voting.model.Dish;
import ru.vogulev.voting.repository.DishRepository;
import ru.vogulev.voting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.vogulev.voting.util.validation.ValidationUtil.*;

@Slf4j
@Service("dishService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@AllArgsConstructor
public class DishService {

    private RestaurantRepository restaurantRepository;
    private DishRepository dishRepository;

    public Dish create(Dish dish, int restaurantId) {
        log.info("create {}", dish);
        checkNew(dish);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        return dishRepository.save(dish);
    }

    public void delete(int id, int restaurantId) {
        log.info("delete {}", id);
        checkNotFoundWithId(dishRepository.deleteByIdAndRestaurantId(id, restaurantId) != 0, id);
    }

    public Optional<Dish> get(int id, int restaurantId) {
        log.info("get {}", id);
        return dishRepository.findByIdAndRestaurantId(id, restaurantId);
    }

    public List<Dish> getAll(int restaurantId) {
        log.info("getAll");
        return dishRepository.findAllByRestaurantIdOrderByAddDate(restaurantId);
    }

    public void update(Dish dish, int id, int restaurantId) {
        log.info("update {} with id={}", dish, id);
        assureIdConsistent(dish, id);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        dishRepository.save(dish);
    }

    public List<Dish> getFromRestaurantByDate(int restaurantId, LocalDate addDate) {
        log.info("get menu from restaurant with id {} on date={}", restaurantId, addDate);
        return dishRepository.findAllByRestaurantIdAndAddDate(restaurantId, addDate);
    }
}
